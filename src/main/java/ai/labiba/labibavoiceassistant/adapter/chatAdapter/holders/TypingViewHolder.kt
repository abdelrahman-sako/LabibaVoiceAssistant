package ai.labiba.labibavoiceassistant.adapter.chatAdapter.holders

import ai.labiba.labibavoiceassistant.R
import ai.labiba.labibavoiceassistant.sdkSetupClasses.LabibaVAInternal
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class TypingViewHolder(private val itemView: View) : ViewHolder(itemView), () -> Unit {
    private val parentLayout: ConstraintLayout = itemView.findViewById(R.id.typingParentLayout)
    private val dotOne: View = itemView.findViewById(R.id.typingDotOne)
    private val dotTwo: View = itemView.findViewById(R.id.typingDotTwo)
    private val dotThree: View = itemView.findViewById(R.id.typingDotThree)

    private var duration = 1000L

    fun onBind() {

        //change dot color based on theme class
        dotOne.background.colorFilter = PorterDuffColorFilter(Color.parseColor(LabibaVAInternal.labibaVaTheme.general.typingIndicatorColor),PorterDuff.Mode.SRC)
        dotTwo.background.colorFilter = PorterDuffColorFilter(Color.parseColor(LabibaVAInternal.labibaVaTheme.general.typingIndicatorColor),PorterDuff.Mode.SRC)
        dotThree.background.colorFilter = PorterDuffColorFilter(Color.parseColor(LabibaVAInternal.labibaVaTheme.general.typingIndicatorColor),PorterDuff.Mode.SRC)

        startAnimation(duration, this)
    }

    private fun startAnimation(animationDuration: Long = 2000, finishListener: () -> Unit) {
        dotOne.animate().scaleX(0.1f).scaleY(0.1f).setDuration(animationDuration / 2)
            .withEndAction {
                dotOne.animate().scaleX(1f).scaleY(1f).setDuration(animationDuration / 2).start()
            }.start()

        dotTwo.animate().scaleX(0.1f).scaleY(0.1f).setStartDelay(animationDuration / 10)
            .setDuration(animationDuration / 2).withEndAction {
                dotTwo.animate().scaleX(1f).scaleY(1f).setDuration(animationDuration / 2).start()
            }.start()

        dotThree.animate().scaleX(0.1f).scaleY(0.1f).setStartDelay(animationDuration / 5)
            .setDuration(animationDuration / 2).withEndAction {
                dotThree.animate().scaleX(1f).scaleY(1f).setDuration(animationDuration / 2)
                    .withEndAction {
                        finishListener.invoke()
                    }.start()
            }.start()
    }

    override fun invoke() {
        startAnimation(duration, this)
    }
}