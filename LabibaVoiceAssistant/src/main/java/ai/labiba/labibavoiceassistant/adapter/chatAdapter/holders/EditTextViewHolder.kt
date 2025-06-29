package ai.labiba.labibavoiceassistant.adapter.chatAdapter.holders

import ai.labiba.labibavoiceassistant.R
import ai.labiba.labibavoiceassistant.interfaces.LabibaChatAdapterCallbackInterface
import ai.labiba.labibavoiceassistant.models.Chat
import ai.labiba.labibavoiceassistant.sdkSetupClasses.LabibaVAInternal
import ai.labiba.labibavoiceassistant.utils.ext.setColorAlpha
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.transition.TransitionManager

class EditTextViewHolder(
    private val itemView: View,
    private val callback: LabibaChatAdapterCallbackInterface?
) : ViewHolder(itemView) {

    fun onBind(item: Chat) {
        val root = itemView.findViewById<ConstraintLayout>(R.id.itemEditTextRoot)
        val edittext = itemView.findViewById<EditText>(R.id.edittext7)

        //set UI
        edittext.setTextColor(
            LabibaVAInternal.labibaVaTheme.userText.textColor.toColorInt().setColorAlpha(110)
        )
        edittext.setHintTextColor(
            LabibaVAInternal.labibaVaTheme.userText.textColor.toColorInt().setColorAlpha(110)
        )
        edittext.textSize = LabibaVAInternal.labibaVaTheme.userText.textSize.toFloat()
        edittext.showSoftInputOnFocus = true


        edittext.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(
                p0: TextView?,
                p1: Int,
                p2: KeyEvent?
            ): Boolean {
                if (p1 == EditorInfo.IME_ACTION_SEND) {

                    //animate change
                    TransitionManager.beginDelayedTransition(root)

                    //change text color
                    edittext.setTextColor(LabibaVAInternal.labibaVaTheme.userText.textColor.toColorInt())

                    //scale down text size
                    edittext.textSize =
                        LabibaVAInternal.labibaVaTheme.userText.scaledDownTextSize.toFloat()
                    edittext.isEnabled = false
                    callback?.onInputTextDone(edittext.text.toString())


                    return true
                }
                return false
            }

        })


    }


}