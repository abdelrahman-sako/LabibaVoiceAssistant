package ai.labiba.labibavoiceassistant.interfaces

import android.widget.TextView
import com.google.android.material.progressindicator.LinearProgressIndicator

interface LabibaChatAdapterCallbackInterface {

    fun onChoicesClick(name:String)

    fun onVideoClick(url:String)

    fun onAudioStateChange(view:LinearProgressIndicator,durationTextView:TextView,isPlaying:Boolean = false)
}