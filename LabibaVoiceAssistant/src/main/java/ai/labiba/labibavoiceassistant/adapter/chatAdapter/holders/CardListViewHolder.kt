package ai.labiba.labibavoiceassistant.adapter.chatAdapter.holders

import ai.labiba.labibavoiceassistant.R
import ai.labiba.labibavoiceassistant.interfaces.LabibaChatAdapterCallbackInterface
import ai.labiba.labibavoiceassistant.models.Chat
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load

class CardListViewHolder(
    private val itemView: View,
    private val callback: LabibaChatAdapterCallbackInterface?
) : ViewHolder(itemView) {
    private val container =
        itemView.findViewById<LinearLayout>(R.id.item_card_list_horizontal_scroll_view_content_linear_layout)

    fun onBind(item: Chat) {
        val type = item.cards?.first()?.title?.split(":")?.get(0)

        container.removeAllViews()

        when(type?.lowercase()){
            "carousel"->{
                carouselOnBind(item)
            }
        }



    }


    private fun carouselOnBind(item: Chat){

        item.cards?.forEach { card ->
            val view = View.inflate(container.context, R.layout.item_carousel_card, null)

            val cardImage = view.findViewById<ImageView>(R.id.cardImage)
            val cardTitle = view.findViewById<TextView>(R.id.cardTitle)
            val cardSubtitle = view.findViewById<TextView>(R.id.cardSubtitle)

            val titleText = card.title.split(":").getOrNull(1)

            cardImage.load(card.imageUrl) {
                crossfade(true)
                placeholder(R.color.black_10)
            }

            cardTitle.text = titleText
            cardSubtitle.text = card.subtitle

            view.setOnClickListener {
                callback?.onCardClick(card.buttons?.first()?.payload?:card.title)
            }

            container.addView(view)

        }

    }


}