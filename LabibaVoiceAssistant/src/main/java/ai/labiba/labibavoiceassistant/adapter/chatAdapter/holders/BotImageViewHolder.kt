package ai.labiba.labibavoiceassistant.adapter.chatAdapter.holders

import ai.labiba.labibavoiceassistant.R
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load

class BotImageViewHolder(private val itemView:View): ViewHolder(itemView) {
    private val botImageView = itemView.findViewById<ImageFilterView>(R.id.item_image_bot_image_filter_view)


    fun onBind(url:String){

        botImageView.load(url){
            crossfade(true)
        }

        botImageView.alpha = 0f
        botImageView.animate().alpha(1f).setInterpolator(OvershootInterpolator()).setDuration(400).start()

    }

}