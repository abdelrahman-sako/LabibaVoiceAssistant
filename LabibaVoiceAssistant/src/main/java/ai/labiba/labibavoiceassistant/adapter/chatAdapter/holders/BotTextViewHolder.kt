package ai.labiba.labibavoiceassistant.adapter.chatAdapter.holders

import ai.labiba.labibavoiceassistant.R
import ai.labiba.labibavoiceassistant.sdkSetupClasses.LabibaVAInternal
import ai.labiba.labibavoiceassistant.utils.ext.cleanup
import ai.labiba.labibavoiceassistant.utils.ext.gone
import ai.labiba.labibavoiceassistant.utils.ext.visible
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.transition.TransitionManager
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.ShapeAppearanceModel

class BotTextViewHolder(private val itemView: View) : ViewHolder(itemView) {
    private var heldItem: String? = null
    private val botText: TextView = itemView.findViewById(R.id.botTextView)
    private val botCard: MaterialCardView = itemView.findViewById(R.id.labiba_va_bot_text_card_view)
    private val contentConstraintLayout = itemView.findViewById<ConstraintLayout>(R.id.botTextCardContentContainer)
    private val theme = LabibaVAInternal.labibaVaTheme.botText

    fun onBind(text: String,scaledDown:Boolean) {

        if(scaledDown || text.length > 100){
            botText.textSize = theme.scaledDownTextSize.toFloat()
        }else{
            botText.textSize = theme.textSize.toFloat()
        }
        heldItem = text
        botText.text = text.cleanup()


        botText.setTextColor(Color.parseColor(theme.textColor))

        //card
        botCard.shapeAppearanceModel = ShapeAppearanceModel.Builder().apply {
            setTopLeftCornerSize(theme.cardTopLeftCorner)
            setTopRightCornerSize(theme.cardTopRightCorner)
            setBottomLeftCornerSize(theme.cardBottomLeftCorner)
            setBottomRightCornerSize(theme.cardBottomRightCorner)
        }.build()

        botCard.strokeWidth = theme.cardStrokeWith
        botCard.strokeColor = Color.parseColor(theme.cardStrokeColor)

        if(theme.cardBackgroundGradientDrawable!=null){
            contentConstraintLayout.background = theme.cardBackgroundGradientDrawable
        }else{
            contentConstraintLayout.background = ColorDrawable(Color.TRANSPARENT)
            botCard.setCardBackgroundColor(Color.parseColor(theme.cardBackgroundColor))
        }
    }


}