package ai.labiba.labibavoiceassistant.adapter.chatAdapter.holders

import ai.labiba.labibavoiceassistant.R
import ai.labiba.labibavoiceassistant.sdkSetupClasses.LabibaVAInternal
import ai.labiba.labibavoiceassistant.utils.ext.cleanup
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.text.Html
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.ShapeAppearanceModel

class BotTextViewHolder(private val itemView: View) : ViewHolder(itemView) {
    private var heldItem: String? = null
    private val botText: TextView = itemView.findViewById(R.id.botTextView)
    private val botCard: MaterialCardView = itemView.findViewById(R.id.labiba_va_bot_text_card_view)
    private val contentConstraintLayout =
        itemView.findViewById<ConstraintLayout>(R.id.botTextCardContentContainer)
    private val theme = LabibaVAInternal.labibaVaTheme.botText

    fun onBind(text: String, scaledDown: Boolean) {

        if (scaledDown || text.length > 100) {
            botText.textSize = theme.scaledDownTextSize.toFloat()
        } else {
            botText.textSize = theme.textSize.toFloat()
        }
        heldItem = text
        botText.text = text.cleanup()


        botText.setTextColor(Color.parseColor(theme.textColor))

        var botString = text

        val matcher = Patterns.WEB_URL.matcher(botString)
        if (matcher.find()) {
            if (matcher.group().contains("www") || matcher.group().contains("http")) {
                val url = "<font color=\"#0000EE\">" + matcher.group() + "</font>"
                botString = botString.replace(matcher.group(), url)
            }
        }

        if (Build.VERSION.SDK_INT >= 24) {
            botText.text = Html.fromHtml(botString, Html.FROM_HTML_MODE_COMPACT)
        } else {
            botText.text = Html.fromHtml(botString)
        }

        botText.setOnClickListener(View.OnClickListener {
            if (Patterns.WEB_URL.matcher(text)
                    .find()
            ) {
                val url = matcher.group()
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    botText.context.startActivity(intent)

                } catch (e: Exception) {
                    Log.e("BotTextViewHolder", "onBind: ${e.message}")
                }


            }
        })


        //card
        botCard.shapeAppearanceModel = ShapeAppearanceModel.Builder().apply {
            setTopLeftCornerSize(theme.cardTopLeftCorner)
            setTopRightCornerSize(theme.cardTopRightCorner)
            setBottomLeftCornerSize(theme.cardBottomLeftCorner)
            setBottomRightCornerSize(theme.cardBottomRightCorner)
        }.build()

        botCard.strokeWidth = theme.cardStrokeWith
        botCard.strokeColor = Color.parseColor(theme.cardStrokeColor)

        if (theme.cardBackgroundGradientDrawable != null) {
            contentConstraintLayout.background = theme.cardBackgroundGradientDrawable
        } else {
            contentConstraintLayout.background = ColorDrawable(Color.TRANSPARENT)
            botCard.setCardBackgroundColor(Color.parseColor(theme.cardBackgroundColor))
        }
    }


}