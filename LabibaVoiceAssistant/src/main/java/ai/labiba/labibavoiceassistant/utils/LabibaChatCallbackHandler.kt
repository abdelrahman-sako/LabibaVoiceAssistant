package ai.labiba.labibavoiceassistant.utils

import ai.labiba.labibavoiceassistant.R
import ai.labiba.labibavoiceassistant.enums.MessageTypes
import ai.labiba.labibavoiceassistant.interfaces.LabibaChatAdapterCallbackInterface
import ai.labiba.labibavoiceassistant.sdkSetupClasses.LabibaVAInternal
import ai.labiba.labibavoiceassistant.ui.dialogs.mainDialog.MainDialogViewModel
import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.media3.common.MediaItem
import androidx.media3.ui.PlayerView
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


internal class LabibaChatCallbackHandler(
    private val viewModel: MainDialogViewModel,
    private val activity: Activity,
    private val lifecycleScope: LifecycleCoroutineScope,
    private val sharedUtils: SharedUtils = SharedUtils(activity)
) : LabibaChatAdapterCallbackInterface {

    private var audioJob: Job? = null

    override fun onChoicesClick(name: String) {
        TTSTools.stopAndClearAudio()

        viewModel.requestMessage(
            MessageTypes.CHOICE.convertToModel(
                name,
                sharedUtils.getSenderId()
            )
        )
    }

    override fun onCardClick(payload: String) {
        TTSTools.stopAndClearAudio()

        viewModel.requestMessage(
            MessageTypes.CARD.convertToModel(
                payload,
                sharedUtils.getSenderId()
            )
        )
    }

    override fun onVideoClick(url: String) {
        val view = activity.layoutInflater.inflate(R.layout.dialog_video, null)
        val player = LabibaVAInternal.exoPlayer

        val dialog = AlertDialog.Builder(activity).create()

        dialog.setView(view)

        //make original background transparent
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.setOnShowListener {
            val playerView = dialog.findViewById<PlayerView>(R.id.dialog_video_exo_player_view)
            playerView?.player = player

            val mediaItem = MediaItem.fromUri(url)
            player?.setMediaItem(mediaItem)
            player?.prepare()
            player?.play()
        }

        dialog.setOnDismissListener {
            player?.stop()
        }



        dialog.show()


    }

    override fun onAudioStateChange(
        view: LinearProgressIndicator,
        durationTextView: TextView,
        isPlaying: Boolean
    ) {

        if (isPlaying) {
            audioJob = lifecycleScope.launch {
                while (true) {
                    delay(500)

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        view.setProgress(
                            (LabibaVAInternal.exoPlayer?.currentPosition)?.toInt() ?: 0, true
                        )
                    } else {
                        view.progress = (LabibaVAInternal.exoPlayer?.currentPosition)?.toInt() ?: 0
                    }

                    durationTextView.text = formatMilliseconds(
                        (LabibaVAInternal.exoPlayer?.currentPosition ?: 0) / 2
                    ) + " / " + formatMilliseconds((LabibaVAInternal.exoPlayer?.duration ?: 0) / 2)

                    view.max = LabibaVAInternal.exoPlayer?.duration?.toInt() ?: 0

                    if (view.progress == view.max) {
                        return@launch
                    }
                }
            }
        } else {
            audioJob?.cancel()
        }

    }

    override fun onInputTextDone(text: String) {
        TTSTools.stopAndClearAudio()

        viewModel.requestMessage(
            MessageTypes.TEXT.convertToModel(
                text,
                sharedUtils.getSenderId()
            )
        )
    }

    private fun formatMilliseconds(milliseconds: Long): String {
        val format = SimpleDateFormat("mm:ss", Locale.getDefault())
        return format.format(Date(milliseconds))
    }


}