package ai.labiba.labibavoiceassistant.adapter.chatAdapter.holders

import ai.labiba.labibavoiceassistant.R
import ai.labiba.labibavoiceassistant.sdkSetupClasses.LabibaVAInternal
import ai.labiba.labibavoiceassistant.utils.ext.cleanup
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class UserTextViewHolder(private val itemView: View) : ViewHolder(itemView) {
    private var heldItem: String? = null
    private val userText: TextView = itemView.findViewById(R.id.userTextView)


    fun onBind(text: String) {
        heldItem = text
        userText.text = text.cleanup()
        userText.textSize = LabibaVAInternal.labibaVaTheme.userText.textSize.toFloat()
        userText.setTextColor(Color.parseColor(LabibaVAInternal.labibaVaTheme.userText.textColor))


    }

}