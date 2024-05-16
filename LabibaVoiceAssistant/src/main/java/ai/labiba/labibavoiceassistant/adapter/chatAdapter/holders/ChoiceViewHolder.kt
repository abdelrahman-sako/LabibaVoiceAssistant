package ai.labiba.labibavoiceassistant.adapter.chatAdapter.holders

import ai.labiba.labibavoiceassistant.R
import ai.labiba.labibavoiceassistant.interfaces.LabibaChatAdapterCallbackInterface
import ai.labiba.labibavoiceassistant.models.Chat
import ai.labiba.labibavoiceassistant.sdkSetupClasses.LabibaVAInternal
import ai.labiba.labibavoiceassistant.utils.ext.gone
import ai.labiba.labibavoiceassistant.utils.ext.toPx
import ai.labiba.labibavoiceassistant.utils.ext.visible
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.shape.ShapeAppearanceModel
import kotlin.random.Random

class ChoiceViewHolder(private val itemView:View,private val callback: LabibaChatAdapterCallbackInterface?): RecyclerView.ViewHolder(itemView) {
    private val chipGroup = itemView.findViewById<ChipGroup>(R.id.item_choice_chip_group)
    private val titleTextView = itemView.findViewById<TextView>(R.id.item_choice_title_text_view)
    private val choiceTheme = LabibaVAInternal.labibaVaTheme.choices

    fun onBind(item:Chat){
        //always remove all views before adding any, this fixes a bug where old choices would not be removed
        chipGroup.removeAllViews()

        chipGroup.chipSpacingHorizontal = choiceTheme.horizontalSpacing
        chipGroup.chipSpacingVertical = choiceTheme.verticalSpacing


        //title text
        if(item.text.isNullOrEmpty()){
            titleTextView.gone()
        }else{
            titleTextView.visible()
            titleTextView.text = item.text
        }

        //apply title theme
        titleTextView.textSize = choiceTheme.titleSize.toFloat()
        titleTextView.setTextColor(Color.parseColor(choiceTheme.titleColor))


        item.choices?.forEach {
            val chip = Chip(itemView.context)
            chip.text = it.title

            //required for vertical spacing to work
            chip.setEnsureMinTouchTargetSize(false)

            //bug fix for arabic
            chip.setPadding(0, 0, 0, 1.toPx)


            //apply theme
            //text
            chip.textSize = choiceTheme.textSize.toFloat()
            chip.setTextColor(Color.parseColor(choiceTheme.textColor))

            //background color
            chip.chipBackgroundColor = ColorStateList.valueOf(Color.parseColor(choiceTheme.backgroundColor))

            //radius
            chip.shapeAppearanceModel = ShapeAppearanceModel.Builder().apply {
                setAllCornerSizes(choiceTheme.radius.toFloat())
            }.build()

            //stroke
            chip.chipStrokeColor = ColorStateList.valueOf(Color.parseColor(choiceTheme.strokeColor))
            chip.chipStrokeWidth = choiceTheme.strokeWidth.toFloat()


            //animate chip
            chip.alpha =0f
            chip.scaleX =0f
            chip.scaleY =0f

            val startDelay = Random.nextLong(0,400)

            chip.animate().alpha(1f).setDuration(600).setStartDelay(startDelay).start()
            chip.animate().scaleX(1f).scaleY(1f).setDuration(400).setStartDelay(startDelay).setInterpolator(OvershootInterpolator()).start()

            //add chip to chipgroup
            chipGroup.addView(chip)

            //click listener
            chip.setOnClickListener {
                callback?.onChoicesClick(chip.text.toString())
            }
        }
    }

}

