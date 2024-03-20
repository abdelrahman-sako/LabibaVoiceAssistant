package ai.labiba.labibavoiceassistant.adapter.chatAdapter.holders

import ai.labiba.labibavoiceassistant.R
import ai.labiba.labibavoiceassistant.sdkSetupClasses.LabibaVAInternal
import ai.labiba.labibavoiceassistant.utils.ext.cleanup
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.transition.TransitionManager

class BotTextViewHolder(private val itemView: View) : ViewHolder(itemView) {
    private var heldItem: String? = null
    private val botText: TextView = itemView.findViewById(R.id.botTextView)


    fun onBind(text: String,scaledDown:Boolean) {

        if(scaledDown || text.length > 100){
            botText.textSize = LabibaVAInternal.labibaVaTheme.botText.scaledDownTextSize.toFloat()
        }else{
            botText.textSize = LabibaVAInternal.labibaVaTheme.botText.textSize.toFloat()
        }
        heldItem = text
        botText.text = text.cleanup()


        botText.setTextColor(Color.parseColor(LabibaVAInternal.labibaVaTheme.botText.textColor))
    }


}