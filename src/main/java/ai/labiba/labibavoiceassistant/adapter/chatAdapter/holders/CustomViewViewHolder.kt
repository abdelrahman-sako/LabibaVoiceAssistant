package ai.labiba.labibavoiceassistant.adapter.chatAdapter.holders

import ai.labiba.labibavoiceassistant.R
import ai.labiba.labibavoiceassistant.models.Chat
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class CustomViewViewHolder(private val itemView:View):ViewHolder(itemView){

    fun bind(item:Chat){

        if(item.customView?.parent != null) {
            (item.customView?.parent as ViewGroup).removeView(item.customView)
        }
        (itemView as ViewGroup).addView(item.customView)
    }

}