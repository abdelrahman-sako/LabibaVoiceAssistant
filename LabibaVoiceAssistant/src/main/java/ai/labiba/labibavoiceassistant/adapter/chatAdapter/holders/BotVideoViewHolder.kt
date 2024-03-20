package ai.labiba.labibavoiceassistant.adapter.chatAdapter.holders

import ai.labiba.labibavoiceassistant.R
import ai.labiba.labibavoiceassistant.interfaces.LabibaChatAdapterCallbackInterface
import android.view.View
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.ImageLoader
import coil.decode.VideoFrameDecoder
import coil.load

class BotVideoViewHolder(private val itemView:View,private val callback: LabibaChatAdapterCallbackInterface?): ViewHolder(itemView) {
    private val imageView:ImageFilterView = itemView.findViewById(R.id.item_video_image_filter_view)

    private val imageLoader = ImageLoader.Builder(itemView.context)
        .components {
            add(VideoFrameDecoder.Factory())
        }
        .build()


    fun onBind(url:String){


        //load thumbnail
        imageView.load(url,imageLoader){
            crossfade(true)
        }

        itemView.setOnClickListener {
            callback?.onVideoClick(url)
        }

    }

}