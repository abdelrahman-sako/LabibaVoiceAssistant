package ai.labiba.labibavoiceassistant.sdkSetupClasses

import ai.labiba.labibavoiceassistant.adapter.chatAdapter.callbackInterfaces.LabibaUserChatInjectionCallbackInterface
import ai.labiba.labibavoiceassistant.databinding.DialogMainBinding
import ai.labiba.labibavoiceassistant.enums.ChatType
import ai.labiba.labibavoiceassistant.interfaces.LabibaVaDataCallbackInterface
import ai.labiba.labibavoiceassistant.models.Chat
import ai.labiba.labibavoiceassistant.models.Messaging
import ai.labiba.labibavoiceassistant.other.Constants
import ai.labiba.labibavoiceassistant.sdkSetupClasses.builders.labiba_theme.LabibaVAThemeSettings
import ai.labiba.labibavoiceassistant.utils.ext.changeStatusBarColor
import ai.labiba.labibavoiceassistant.utils.ext.getCurrentStatusBarColor
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import android.speech.RecognizerIntent
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import coil.Coil
import coil.util.CoilUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

internal object LabibaVAInternal {

    var labibaVaTheme:LabibaVATheme = LabibaVATheme()
    var mLabibaVaDataCallback:LabibaVaDataCallbackInterface? = null
    internal var mLabibaUserInjectionCallback:LabibaUserChatInjectionCallbackInterface? = null
    internal var dialog: BottomSheetDialogFragment? = null


     fun handleResponse(
        response: Messaging.Response,
        onComplete: (List<Chat>) -> Unit,
        onSendVoice: ((String) -> Unit)
    ) {

        val dataList: ArrayList<Chat> = arrayListOf()

         response.forEachIndexed { index, entity ->
            val textMessage = entity.message.text
            val choiceMessage = entity.message.quickReplies
            val attachmentMessage = entity.message.attachment


            if (!textMessage.isNullOrEmpty()) {
                val messageSplitter = textMessage.split("@:@")


                if (messageSplitter.size > 1) {
                    onSendVoice(messageSplitter[1])
                } else {
                    onSendVoice(messageSplitter[0])
                }

//                    botTextResponse(messageSplitter[0])
                dataList.add(
                    Chat().setData(
                        chatType = ChatType.BOT_TEXT,
                        textMessage = messageSplitter[0]
                    )
                )

                if (!choiceMessage.isNullOrEmpty()) {
//                        delay(200)
//                        botChoicesResponse(choiceMessage)
                    dataList.add(
                        Chat().setData(
                            chatType = ChatType.CHOICES,
                            choices = choiceMessage
                        )
                    )
                }

            } else if (!choiceMessage.isNullOrEmpty()) {

//                    botChoicesResponse(choiceMessage)
                dataList.add(
                    Chat().setData(
                        chatType = ChatType.CHOICES,
                        choices = choiceMessage
                    )
                )

            } else if (attachmentMessage != null) {

                if (attachmentMessage.type.equals(
                        "template", true
                    ) && attachmentMessage.payload != null
                ) {
                    if (attachmentMessage.payload.elements.isNotEmpty()) {
                        val firstCard = attachmentMessage.payload.elements[0]
                        firstCard.imageAspectRatio = attachmentMessage.payload.imageAspectRatio

                        dataList.add(
                            Chat().setData(
                                chatType = ChatType.CARDS,
                                cards = attachmentMessage.payload.elements
                            )
                        )
//                            botCardResponse(attachmentMessage.payload.elements)
                    }
                }

            }

         }

         onComplete.invoke(dataList)

     }


     fun getRecognizerIntent(packageName:String): Intent {
        return Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Constants.voiceLanguage)

//            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
            if (Build.VERSION.SDK_INT <= 24) putExtra("android.speech.extra.DICTATION_MODE", true)

            if (Build.VERSION.SDK_INT >= 23) {
                putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, false)
                //Todo check if offline needed from the settings and change the value
            }
        }
    }

    fun setupGeneralTheme(binding: DialogMainBinding,fragment: Fragment){

        if (labibaVaTheme.general.statusBarColor !=null) {
            //save old status bar color in order to change it back on dialog exit
            labibaVaTheme.general.oldStatusBarColor = fragment.getCurrentStatusBarColor()

            //change status bar color
            fragment.changeStatusBarColor(LabibaVAInternal.labibaVaTheme.general.statusBarColor)
        }

        //background color
        binding.labibaVaDialogConstraintLayout.background.colorFilter = PorterDuffColorFilter(Color.parseColor(labibaVaTheme.general.backgroundColor),PorterDuff.Mode.SRC)
        binding.mainVaWaveLineView.setBackGroundColor((Color.parseColor(labibaVaTheme.general.backgroundColor)))

        //wave view color
        binding.mainVaWaveLineView.setLineColor(Color.parseColor(labibaVaTheme.general.wavesColor))

        //mic icon and background color
        binding.mainVaMicBtnImageFilterView.imageTintList = ColorStateList.valueOf(Color.parseColor(
            labibaVaTheme.general.micIconColor))
        binding.mainVaMicBtnImageFilterView.setBackgroundColor(Color.parseColor(labibaVaTheme.general.micBackgroundColor))

    }

    fun setLabibaVaDataCallback(labibaVaDataCallback: LabibaVaDataCallbackInterface){
        this.mLabibaVaDataCallback = labibaVaDataCallback
    }

    fun injectCustomView(customView: View) {
        mLabibaUserInjectionCallback?.injectCustomView(customView)
    }

    fun injectTyping(){
        mLabibaUserInjectionCallback?.injectTyping()
    }

    fun clearChat(){
        mLabibaUserInjectionCallback?.clearChat()
    }

    fun clearTypingAndChoices(){
        mLabibaUserInjectionCallback?.clearTypingAndChoices()
    }


}