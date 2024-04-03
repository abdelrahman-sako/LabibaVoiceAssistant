package ai.labiba.labibavoiceassistant.adapter.chatAdapter.holders

import ai.labiba.labibavoiceassistant.R
import ai.labiba.labibavoiceassistant.sdkSetupClasses.LabibaVAInternal
import ai.labiba.labibavoiceassistant.utils.ext.cleanup
import ai.labiba.labibavoiceassistant.utils.ext.gone
import ai.labiba.labibavoiceassistant.utils.ext.visible
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.ShapeAppearanceModel

class UserTextViewHolder(private val itemView: View) : ViewHolder(itemView) {
    private var heldItem: String? = null
    private val userText: TextView = itemView.findViewById(R.id.userTextView)
    private val userCard: MaterialCardView = itemView.findViewById(R.id.labiba_va_user_text_card_view)
    private val contentConstraintLayout = itemView.findViewById<ConstraintLayout>(R.id.userCardContentContainer)
    private val theme = LabibaVAInternal.labibaVaTheme.userText


    fun onBind(text: String) {
        heldItem = text
        userText.text = text.cleanup()
        userText.textSize = theme.textSize.toFloat()
        userText.setTextColor(Color.parseColor(theme.textColor))


        //card
        userCard.shapeAppearanceModel = ShapeAppearanceModel.Builder().apply {
            setTopLeftCornerSize(theme.cardTopLeftCorner)
            setTopRightCornerSize(theme.cardTopRightCorner)
            setBottomLeftCornerSize(theme.cardBottomLeftCorner)
            setBottomRightCornerSize(theme.cardBottomRightCorner)
        }.build()

        userCard.strokeWidth = theme.cardStrokeWith
        userCard.strokeColor = Color.parseColor(theme.cardStrokeColor)

        if(theme.cardBackgroundGradientDrawable!=null){
            contentConstraintLayout.background = theme.cardBackgroundGradientDrawable
        }else{
            contentConstraintLayout.background = ColorDrawable(Color.TRANSPARENT)
            userCard.setCardBackgroundColor(Color.parseColor(theme.cardBackgroundColor))
        }

    }

}