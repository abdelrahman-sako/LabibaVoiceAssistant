package ai.labiba.labibavoiceassistant.adapter.chatAdapter.holders

import ai.labiba.labibavoiceassistant.R
import ai.labiba.labibavoiceassistant.utils.ext.toPx
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.transform.RoundedCornersTransformation

class BotImageViewHolder(private val itemView:View): ViewHolder(itemView) {
    private val botImageView = itemView.findViewById<ImageFilterView>(R.id.item_image_bot_image_filter_view)


    fun onBind(url:String){

        botImageView.load(url){
            crossfade(true)
        }

        botImageView.alpha = 0f
        botImageView.animate().alpha(1f).setInterpolator(OvershootInterpolator()).setDuration(400).start()

        botImageView.setOnClickListener {
            if(botImageView.drawable == null) return@setOnClickListener

            val dialog = AlertDialog.Builder(itemView.context).create()

            //set background to transparent for dialog in order to show rounded corners of the image
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


            val linearLayout = LinearLayout(itemView.context)
            val dialogImageView = ImageFilterView(itemView.context)


            dialogImageView.layoutParams =
                LinearLayout.LayoutParams(
                    botImageView.drawable.intrinsicWidth,
                    botImageView.drawable.intrinsicHeight
                )

            dialogImageView.setImageDrawable(botImageView.drawable)
            dialogImageView.round = 30.toPx.toFloat()

            linearLayout.addView(dialogImageView)

            //add image view to dialog
            dialog.setView(linearLayout)

            dialog.show()
        }
    }

}