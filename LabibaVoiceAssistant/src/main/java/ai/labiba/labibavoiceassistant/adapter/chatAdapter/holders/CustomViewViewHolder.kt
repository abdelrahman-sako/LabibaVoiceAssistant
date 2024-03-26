package ai.labiba.labibavoiceassistant.adapter.chatAdapter.holders

import ai.labiba.labibavoiceassistant.R
import ai.labiba.labibavoiceassistant.models.Chat
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class CustomViewViewHolder(private val itemView:View):ViewHolder(itemView){
    private val constraintLayout = itemView.findViewById<ConstraintLayout>(R.id.item_custom_view_constraint_layout)

    fun bind(item:Chat){

        try {
            //always remove all views before adding any, this fixes a bug where old images would not be removed
            constraintLayout.removeAllViews()

            if (item.customView?.parent != null) {
                (item.customView?.parent as ViewGroup).removeView(item.customView)
            }
            (itemView as ViewGroup).addView(item.customView)

        }catch (e:Exception){
            e.printStackTrace()
        }
    }

}