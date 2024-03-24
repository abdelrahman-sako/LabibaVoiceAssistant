package ai.labiba.labibavoiceassistant.ui.dialogs.mainDialog

import ai.labiba.labibavoiceassistant.R
import ai.labiba.labibavoiceassistant.adapter.chatAdapter.ChatAdapter
import ai.labiba.labibavoiceassistant.databinding.DialogMainBinding
import ai.labiba.labibavoiceassistant.enums.LabibaLanguages
import ai.labiba.labibavoiceassistant.enums.MessageTypes
import ai.labiba.labibavoiceassistant.interfaces.LabibaUserChatInjectionCallbackInterface
import ai.labiba.labibavoiceassistant.interfaces.RecognitionVACallbacks
import ai.labiba.labibavoiceassistant.models.Chat
import ai.labiba.labibavoiceassistant.other.Constants
import ai.labiba.labibavoiceassistant.sdkSetupClasses.LabibaApiResponseHandle.handleResponse
import ai.labiba.labibavoiceassistant.sdkSetupClasses.LabibaVAInternal
import ai.labiba.labibavoiceassistant.sdkSetupClasses.LabibaVAInternal.exoPlayer
import ai.labiba.labibavoiceassistant.utils.LabibaChatCallbackHandler
import ai.labiba.labibavoiceassistant.utils.MicPermissionFragment
import ai.labiba.labibavoiceassistant.utils.SharedUtils
import ai.labiba.labibavoiceassistant.utils.TTSTools
import ai.labiba.labibavoiceassistant.utils.Tools
import ai.labiba.labibavoiceassistant.utils.Views
import ai.labiba.labibavoiceassistant.utils.apiHandleResponse.Resource
import ai.labiba.labibavoiceassistant.utils.dialogs.CustomBottomSheetDialogFragment
import ai.labiba.labibavoiceassistant.utils.ext.changeStatusBarColor
import ai.labiba.labibavoiceassistant.utils.ext.fadeInToVisible
import ai.labiba.labibavoiceassistant.utils.ext.fadeOutToGone
import ai.labiba.labibavoiceassistant.utils.ext.gone
import ai.labiba.labibavoiceassistant.utils.ext.scaleDownToInvisible
import ai.labiba.labibavoiceassistant.utils.ext.scaleUpToVisible
import ai.labiba.labibavoiceassistant.utils.ext.toPx
import ai.labiba.labibavoiceassistant.utils.ext.visible
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import coil.request.onAnimationEnd
import coil.request.onAnimationStart
import coil.request.repeatCount
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.LinkedList
import java.util.Queue
import kotlin.random.Random


class MainDialog : CustomBottomSheetDialogFragment(), RecognitionVACallbacks,
    LabibaUserChatInjectionCallbackInterface {

    //binding
    private var _binding: DialogMainBinding? = null
    private val binding get() = _binding!!

    //view model
    private val viewModel: MainDialogViewModel by viewModels()

    //adapters
    val chatAdapter: ChatAdapter by lazy { ChatAdapter() }

    //shared utils
    private val sharedUtils by lazy { SharedUtils(requireContext()) }

    //speech recognizer
    private var speechRecognizer: SpeechRecognizer? = null

    //Queues
    private val messagesQueue: Queue<Chat> = LinkedList()
    private val mTTSQueue: Queue<Pair<String, LabibaLanguages>> = LinkedList()

    //-------------------------
    private var errorRetryCount = 0


    override fun onResume() {
        super.onResume()
        binding.mainVaWaveLineView.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        alwaysExpanded()


        setupUI()
        setupRecyclerView()
        checkRequiredParameters()
        setupListeners()
        setupObservers()

    }

    private fun setupUI() {
        LabibaVAInternal.dialog = this

        LabibaVAInternal.setupGeneralTheme(binding, this)
        LabibaVAInternal.setExoplayer(requireActivity())

        if (LabibaVAInternal.fullScreen) {

            //remove drag view
            binding.materialCardView.gone()

            //remove rounded top corners background
            binding.labibaVaDialogConstraintLayout.background =
                ColorDrawable(Color.parseColor(LabibaVAInternal.labibaVaTheme.general.backgroundColor))

            //set height to full screen
            binding.labibaVaDialogConstraintLayout.layoutParams.height =
                Views.getScreenHeight(requireActivity())

            //disable swipe down
            (dialog as BottomSheetDialog).behavior.isDraggable = false

        }

    }

    private fun setupRecyclerView() {
        binding.mainVaRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = chatAdapter
        }

        chatAdapter.setCallbackInterface(
            LabibaChatCallbackHandler(
                viewModel,
                requireActivity(),
                lifecycleScope
            )
        )
        LabibaVAInternal.mLabibaUserInjectionCallback = this

    }

    private fun setupSuggestions() {
        lifecycleScope.launch(Dispatchers.Main) {
            delay(200)

            //always remove all views before adding any, this fixes a bug where old choices would not be removed
            binding.mainVaSuggestionChipGroup.removeAllViews()
            val theme = LabibaVAInternal.labibaVaTheme.suggestions

            if (LabibaVAInternal.suggestionList.isNullOrEmpty()) {
                binding.mainVaSuggestionChipGroup.gone()
                return@launch
            }

            //scroll back to first item
            binding.mainVaSuggestionHorizontalScollView.smoothScrollTo(0, 0)

            binding.mainVaSuggestionChipGroup.chipSpacingHorizontal = theme.horizontalSpacing
            binding.mainVaSuggestionChipGroup.chipSpacingVertical = theme.verticalSpacing

            LabibaVAInternal.suggestionList?.shuffled()?.forEach { chipText ->
                val chip = Chip(requireActivity())
                chip.text = chipText

                //required for vertical spacing to work
                chip.setEnsureMinTouchTargetSize(false)

                //apply theme
                //text
                chip.textSize = theme.textSize.toFloat()
                chip.setTextColor(Color.parseColor(theme.textColor))

                //background color
                chip.chipBackgroundColor =
                    ColorStateList.valueOf(Color.parseColor(theme.backgroundColor))

                //radius
                chip.shapeAppearanceModel = ShapeAppearanceModel.Builder().apply {
                    setAllCornerSizes(theme.radius.toFloat())
                }.build()

                //stroke
                chip.chipStrokeColor =
                    ColorStateList.valueOf(Color.parseColor(theme.strokeColor))
                chip.chipStrokeWidth = theme.strokeWidth.toFloat()


                //animate chip
                chip.alpha = 0f
                chip.scaleX = 0f
                chip.scaleY = 0f

                val startDelay = Random.nextLong(0, 400)

                chip.animate().alpha(1f).setDuration(600).setStartDelay(startDelay).start()
                chip.animate().scaleX(1f).scaleY(1f).setDuration(400).setStartDelay(startDelay)
                    .setInterpolator(
                        OvershootInterpolator()
                    ).start()

                //add chip to chipgroup
                binding.mainVaSuggestionChipGroup.addView(chip)

                //click listener
                chip.setOnClickListener {

                    //stop and clear any audio playing/not played yet
                    TTSTools.stopAndClearAudio()

                    //stop message and tts requests
                    viewModel.stopRequests()

                    //clear any messages and TTS in the queue not shown yet
                    messagesQueue.clear()
                    mTTSQueue.clear()

                    chatAdapter.addUserText(chipText)

                    viewModel.requestMessage(
                        MessageTypes.CHOICE.convertToModel(
                            chipText,
                            sharedUtils.getSenderId()
                        )
                    )

                }
            }

        }
    }

    private fun checkRequiredParameters() {
        if (Constants.recipientIds.isEmpty()) {
            chatAdapter.addCustomView(TextView(requireContext()).apply {
                setPaddingRelative(15.toPx, 0, 15.toPx, 0)
                text = context.getString(R.string.recipient_id_not_added)
                setTextColor(Color.RED)
                textSize = 24f
            })
        }
    }

    private fun setupListeners() {

        //mic button
        binding.mainVaMicBtnImageFilterView.setOnClickListener {
            MicPermissionFragment.requestMicrophonePermission(requireActivity().supportFragmentManager)
        }

        //on permission result
        MicPermissionFragment.setupMicrophonePermissionCallback {
            if (it) {//granted


                //show wave line and hide mic button
                binding.mainVaMicBtnImageFilterView.scaleDownToInvisible()
                binding.mainVaWaveLineView.startAnim()
                binding.mainVaWaveLineView.fadeInToVisible()


                //stop and clear any audio playing/not played yet
                TTSTools.stopAndClearAudio()

                //stop message and tts requests
                viewModel.stopRequests()

                //clear any messages and TTS in the queue not shown yet
                messagesQueue.clear()
                mTTSQueue.clear()


                //initialize speech recognizer
                try {
                    if (speechRecognizer != null) speechRecognizer?.destroy()

                    speechRecognizer = null

                    speechRecognizer =
                        SpeechRecognizer.createSpeechRecognizer(requireContext()).apply {
                            setRecognitionListener(this@MainDialog)
                            startListening(LabibaVAInternal.getRecognizerIntent(requireContext().packageName))
                        }

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }

    }

    private fun setupObservers() {
        chatAdapter.addTyping()

        //start bot conversation
        viewModel.startConversation(sharedUtils.getSenderId())

        viewModel.messaging.observe(this) { resource ->
            if (resource != null) {

                when (resource) {
                    is Resource.Loading -> {
                        chatAdapter.cleanList() {
                            chatAdapter.addTyping()
                        }
                        Log.d("RequestLog", "Request loading: ")
                    }

                    is Resource.Success -> {

                        //remove list of typing/cards
                        chatAdapter.cleanList()

                        if (resource.data == null) {
                            Log.e("Labiba_E", "Messaging Response: ${resource.message}")
                            return@observe
                        }


                        //handle response data
                        handleResponse(resource.data, {
                            //add retrieved messages to the message queue
                            it.forEach { message -> messagesQueue.offer(message) }

                            if (LabibaVAInternal.labibaVaTheme.themeSettings.isTextToSpeechEnabled) {
                                //show the first message
                                val chatItem = messagesQueue.poll()
                                if (chatItem != null) {


                                    //animate dialog expand/shrink
                                    val viewG =
                                        dialog?.findViewById<ViewGroup>(com.google.android.material.R.id.design_bottom_sheet)
                                    if (viewG != null) {
                                        TransitionManager.beginDelayedTransition(viewG)
                                    }


                                    chatAdapter.submitList(listOf(chatItem))

                                    setupSuggestions()

                                }


                                //on complete, request TTS using the queue filled below
                                if (mTTSQueue.peek() != null) {
                                    val tts = mTTSQueue.poll()
                                    viewModel.requestTextToSpeech(
                                        tts?.first ?: "",
                                        tts?.second ?: LabibaLanguages.ENGLISH
                                    )

                                }
                            } else {
                                //show all messages
                                chatAdapter.submitList(it)

                            }


                        }, {
                            //fill the TTS queue
                            //This call back is called as the response is being handled
                            //when response is handled onComplete is called and TTS is requested using the queue

                            val language =
                                if (LabibaVAInternal.labibaVaTheme.themeSettings.autoDetectLanguage) {
                                    //change TTS and speechRecognition language if returned language was changed
                                    Tools.detectLabibaLanguageConstant(it ?: "", false)
                                } else {
                                    null
                                }
                            mTTSQueue.offer(Pair(it, language ?: LabibaLanguages.ENGLISH))

                            //change speechRecognition language to last TTS detected language
                            Constants.voiceLanguage = language?.getSrCode() ?: "en-US"
                        })

                    }

                    is Resource.Error -> {
                        chatAdapter.cleanList()
                        Log.d("RequestLog", "Request Error: ${resource.message}")
                    }


                }

            }
        }


        viewModel.speechAudioLink.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {

                        //play audio
                        TTSTools.playAudio(it.data?.audioUrl ?: "") {
                            //Automatically TTSTools plays the next audio on audio play complete

                            lifecycleScope.launch(Dispatchers.Main) {

                                //on audio play complete show next text in chat
                                val chatItem = messagesQueue.poll()

                                if (chatItem != null) {
                                    Tools.addChatItemToAdapterBasedOnType(chatAdapter, chatItem) {
                                        binding.mainVaRecyclerView.smoothScrollToPosition(
                                            chatAdapter.itemCount - 1
                                        )
                                    }
                                }

                                //auto listening
                                if (TTSTools.isQueueEmpty() && LabibaVAInternal.labibaVaTheme.themeSettings.isAutoListening) {
                                    if (_binding != null) {
                                        binding.mainVaMicBtnImageFilterView.performClick()
                                    }
                                }


                                //since the audio must be played in order, the audio is requested one by one using a queue
                                //request the next TTS audio from the queue
                                if (mTTSQueue.peek() != null) {
                                    val tts = mTTSQueue.poll()
                                    viewModel.requestTextToSpeech(
                                        tts?.first ?: "",
                                        tts?.second ?: LabibaLanguages.ENGLISH
                                    )
                                }
                            }
                        }
                    }

                    is Resource.Error -> {
                        //request the next TTS audio from the queue
                        if (mTTSQueue.peek() != null) {
                            val tts = mTTSQueue.poll()
                            viewModel.requestTextToSpeech(
                                tts?.first ?: "",
                                tts?.second ?: LabibaLanguages.ENGLISH
                            )
                        }

                        Log.d("RequestAudioLog", "Request Error: ${it.message}")
                    }
                }
            }

        }

    }


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        if (LabibaVAInternal.labibaVaTheme.general.oldStatusBarColor != null) {
            //change back client status bar color
            this.changeStatusBarColor(LabibaVAInternal.labibaVaTheme.general.oldStatusBarColor)
        }
    }

    override fun onPause() {
        super.onPause()
        //stop and clear any audio playing/not played yet
        TTSTools.stopAndClearAudio()

        //wave line view on pause
        if (_binding != null) {
            binding.mainVaWaveLineView.onPause()
        }

        //stop speech recognition
        try {
            speechRecognizer?.let {
                it.stopListening()
                it.destroy()
            }
            speechRecognizer = null
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onDestroyView() {

        //wave line view on destroy
        if (_binding != null) {
            binding.mainVaWaveLineView.release()
        }

        exoPlayer?.stop()
        exoPlayer?.release()

        LabibaVAInternal.dialog = null

        super.onDestroyView()
        _binding = null
    }

    //-------------------------------------- SPEECH RECOGNITION LISTENERS --------------------------------------
    override fun onResults(p0: Bundle?) {
        //retrieve result data from speech recognition
        val data: ArrayList<String>? =
            p0?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)

        //add user text retrieved from speech recognition to the recycler view
        chatAdapter.addUserText(data?.first() ?: "") { //onUserTextAdded

            //scroll to the newly added message
            binding.mainVaRecyclerView.smoothScrollToPosition(chatAdapter.itemCount - 1)
        }

        //request bot response using the text retrieved from speech recognition
        if (!data.isNullOrEmpty()) {
            viewModel.requestMessage(
                MessageTypes.TEXT.convertToModel(
                    data.first(),
                    sharedUtils.getSenderId()
                )
            )
        }

        binding.mainVaWaveLineView.stopAnim()
        //hide wave line view and show mic button
        binding.mainVaWaveLineView.gone()
        binding.mainVaMicBtnImageFilterView.scaleUpToVisible(500)


    }

    private var skippedRounds = 0
    private var skipNumbers = 20
    override fun onRmsChanged(p0: Float) {

        //control wave line view animation using RMS value
        if (_binding != null) {
            binding.mainVaWaveLineView.startAnim()

            if (skippedRounds >= skipNumbers) {
                binding.mainVaWaveLineView.setVolume(p0.toInt() * 10)
            } else {
                skippedRounds++
            }
        }

        super.onRmsChanged(p0)
    }

    override fun onPartialResults(p0: Bundle?) {
        //retrieve result data from speech recognition
        val data: ArrayList<String>? =
            p0?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)


        //add user text retrieved from speech recognition to the recycler view
        chatAdapter.addOrModifyUserText(data?.first() ?: "")

        super.onPartialResults(p0)

    }


    override fun onError(p0: Int) {


//        var Error = ""
//        val language = Constants.startLanguageName
//
//        when (p0) {
//            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> Error =
//                LabibaSRErrors.ERROR_NETWORK_TIMEOUT.getMessage(language)
//
//            SpeechRecognizer.ERROR_NETWORK -> Error =
//                LabibaSRErrors.ERROR_NETWORK.getMessage(language)
//
//            SpeechRecognizer.ERROR_AUDIO -> Error = LabibaSRErrors.ERROR_AUDIO.getMessage(language)
//            SpeechRecognizer.ERROR_SERVER -> Error =
//                LabibaSRErrors.ERROR_SERVER.getMessage(language)
//
//            SpeechRecognizer.ERROR_CLIENT -> Error =
//                LabibaSRErrors.ERROR_CLIENT.getMessage(language)
//
//            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> Error =
//                LabibaSRErrors.ERROR_SPEECH_TIMEOUT.getMessage(language)
//
//            SpeechRecognizer.ERROR_NO_MATCH -> Error =
//                LabibaSRErrors.ERROR_NO_MATCH.getMessage(language)
//
//            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> Error =
//                LabibaSRErrors.ERROR_RECOGNIZER_BUSY.getMessage(language)
//
//            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> Error =
//                LabibaSRErrors.ERROR_INSUFFICIENT_PERMISSIONS.getMessage(language)
//
//            SpeechRecognizer.ERROR_TOO_MANY_REQUESTS -> Error =
//                LabibaSRErrors.ERROR_TOO_MANY_REQUESTS.getMessage(language)
//
//            SpeechRecognizer.ERROR_SERVER_DISCONNECTED -> Error =
//                LabibaSRErrors.ERROR_SERVER_DISCONNECTED.getMessage(language)
//
//            SpeechRecognizer.ERROR_LANGUAGE_NOT_SUPPORTED -> Error =
//                LabibaSRErrors.ERROR_LANGUAGE_NOT_SUPPORTED.getMessage(language)
//
//            SpeechRecognizer.ERROR_LANGUAGE_UNAVAILABLE -> Error =
//                LabibaSRErrors.ERROR_LANGUAGE_UNAVAILABLE.getMessage(language)
//
//            SpeechRecognizer.ERROR_CANNOT_CHECK_SUPPORT -> Error =
//                LabibaSRErrors.ERROR_CANNOT_CHECK_SUPPORT.getMessage(language)
//        }


        //retry speech recognition three times if error occurs
        if (errorRetryCount <= 1) {
            //re-start speech recognition process
            binding.mainVaMicBtnImageFilterView.performClick()
            errorRetryCount++
        } else {
            errorRetryCount = 0

            binding.mainVaWaveLineView.stopAnim()
            binding.mainVaWaveLineView.gone()
            binding.mainVaMicBtnImageFilterView.scaleUpToVisible()
        }

        super.onError(p0)
    }

    override fun injectCustomView(customView: View) {
        //animate dialog expand/shrink
        val viewG =
            dialog?.findViewById<ViewGroup>(com.google.android.material.R.id.design_bottom_sheet)
        if (viewG != null) {
            TransitionManager.beginDelayedTransition(viewG)
        }

        chatAdapter.addCustomView(customView)
    }

    override fun injectTyping() {
        chatAdapter.addTyping()
    }

    override fun clearChat() {
        chatAdapter.clearAllData()
    }

    override fun clearTypingAndChoices() {
        chatAdapter.cleanList()
    }

    override fun addGifBackground(url: String, elevation: Float, loop: Boolean) {
        val imageLoader = ImageLoader.Builder(requireActivity())
            .components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }.build()


        //create image view
        val imageView = ShapeableImageView(requireActivity())
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.shapeAppearanceModel = ShapeAppearanceModel
            .builder().setTopLeftCorner(CornerFamily.ROUNDED, 30.toPx.toFloat())
            .setTopRightCorner(CornerFamily.ROUNDED, 30.toPx.toFloat()).build()

        //image view is hidden until gif is loaded
        imageView.gone()
        binding.labibaVaDialogConstraintLayout.addView(imageView)

        imageView.layoutParams.apply {
            width = binding.dialogContainer.width
            height = binding.dialogContainer.height
        }

        imageView.elevation = elevation

        //load gif
        imageView.load(url, imageLoader) {
            crossfade(true)

            //show on load
            listener(onSuccess = {_, _ ->
                imageView.fadeInToVisible()
            })

            if (!loop) {
                repeatCount(0)
            }

            onAnimationEnd {
                imageView.fadeOutToGone(){
                    //remove image view after hiding it
                    binding.labibaVaDialogConstraintLayout.removeView(imageView)
                }
            }

        }


    }


}