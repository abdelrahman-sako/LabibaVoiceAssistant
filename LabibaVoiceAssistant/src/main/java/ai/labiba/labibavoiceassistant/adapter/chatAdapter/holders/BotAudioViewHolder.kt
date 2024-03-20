package ai.labiba.labibavoiceassistant.adapter.chatAdapter.holders

import ai.labiba.labibavoiceassistant.R
import ai.labiba.labibavoiceassistant.interfaces.LabibaChatAdapterCallbackInterface
import ai.labiba.labibavoiceassistant.sdkSetupClasses.LabibaVAInternal
import ai.labiba.labibavoiceassistant.utils.ext.disableForSomeTime
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.google.android.material.card.MaterialCardView
import com.google.android.material.progressindicator.LinearProgressIndicator

class BotAudioViewHolder(
    private val itemView: View,
    private val callback: LabibaChatAdapterCallbackInterface?
) : ViewHolder(itemView) {
    private val pauseCard = itemView.findViewById<MaterialCardView>(R.id.item_audio_pause_card)
    private val pauseImageView = itemView.findViewById<ImageView>(R.id.item_audio_pause_image_view)
    private val durationTextView= itemView.findViewById<TextView>(R.id.item_audio_duration_text_view)
    private val progressBar: LinearProgressIndicator =
        itemView.findViewById(R.id.item_audio_linear_progress_bar)
    private val theme =LabibaVAInternal.labibaVaTheme.audio


    fun onBind(url: String) {
        //theme
        pauseImageView.setColorFilter(Color.parseColor(theme.iconColor))
        pauseCard.setCardBackgroundColor(Color.parseColor(theme.buttonBackgroundColor))
        pauseCard.strokeWidth = theme.buttonStrokeWidth
        pauseCard.strokeColor = Color.parseColor(theme.buttonStrokeColor)
        durationTextView.setTextColor(Color.parseColor(theme.durationTextColor))
        durationTextView.textSize = theme.durationTextSize

        progressBar.trackColor = Color.parseColor(theme.trackColor)
        progressBar.trackCornerRadius = theme.trackCornerRadius
        progressBar.trackThickness = theme.trackThickness

        progressBar.setIndicatorColor(Color.parseColor(theme.indicatorColor))



        //audio
        val mediaItem = MediaItem.fromUri(url)
        LabibaVAInternal.exoPlayer?.setMediaItem(mediaItem)
        LabibaVAInternal.exoPlayer?.prepare()
        LabibaVAInternal.exoPlayer?.play()
        callback?.onAudioStateChange(progressBar,durationTextView ,true)

        pauseCard.setOnClickListener {
            it.disableForSomeTime()

            if (LabibaVAInternal.exoPlayer?.isPlaying == true) {
                LabibaVAInternal.exoPlayer?.pause()
            } else if(LabibaVAInternal.exoPlayer?.isPlaying == false) {
                LabibaVAInternal.exoPlayer?.play()
            }

        }

        LabibaVAInternal.exoPlayer?.addListener(object : Player.Listener{
            override fun onPlaybackStateChanged(playbackState: Int) {
                if(playbackState == Player.STATE_ENDED){
                    LabibaVAInternal.exoPlayer?.pause()
                    LabibaVAInternal.exoPlayer?.seekTo(0)
                    callback?.onAudioStateChange(progressBar,durationTextView, false)
                }

                super.onPlaybackStateChanged(playbackState)
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {

                if(isPlaying){
                    callback?.onAudioStateChange(progressBar,durationTextView, true)
                    pauseImageView.load(theme.pauseIconDrawable)
                }else{
                    callback?.onAudioStateChange(progressBar,durationTextView, false)
                    pauseImageView.load(theme.playIconDrawable)
                }

                super.onIsPlayingChanged(isPlaying)
            }
        })

    }

    fun onRecycled() {
        LabibaVAInternal.exoPlayer?.stop()
        callback?.onAudioStateChange(progressBar, durationTextView,false)
    }


}