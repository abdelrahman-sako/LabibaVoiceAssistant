package ai.labiba.labibavoiceassistant.utils.ext

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//disable buttons for some time
fun View.disableForSomeTime(duration: Long = 200) {
    isEnabled = false
    CoroutineScope(Dispatchers.Main).launch {
        delay(duration)
        isEnabled = true
    }
}

//disable click for some time
fun View.disableClickForSomeTime(durationMs: Long = 200) {
    isClickable = false
    CoroutineScope(Dispatchers.Main).launch {
        delay(durationMs)
        isClickable = true
    }
}

//fade out view then set visibility to gone
fun View.fadeOutToGone(durationMs: Long = 200, toAlpha: Float = 0f) {
    if (this.visibility == View.GONE || this.visibility == View.INVISIBLE) {
        return
    }

    this.animate().alpha(toAlpha).duration = durationMs

    CoroutineScope(Dispatchers.Main).launch {
        delay(durationMs)
        this@fadeOutToGone.visibility = View.GONE
    }

}

fun View.animateAlpha(fromAlpha:Float,toAlpha:Float,durationMs: Long = 200){

    this.alpha = fromAlpha
    this.animate().alpha(toAlpha).duration = durationMs
}

fun View.gone(){
    this.visibility = View.GONE
}

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.isViewInBounds(x: Int, y: Int): Boolean {
    val outRect = Rect()
    val location = IntArray(2)
    getDrawingRect(outRect)
    getLocationOnScreen(location)
    outRect.offset(location[0], location[1])
    return outRect.contains(x, y)
}

//fade in view
fun View.fadeInToVisible(durationMs: Long = 200, toAlpha: Float = 1f) {
    if (this.visibility == View.VISIBLE) {
        return
    }

    this.alpha = 0f
    this.visibility = View.VISIBLE
    this.animate().alpha(toAlpha).duration = durationMs


}

fun Any.logd(name:String=""){
    Log.d("TESTLOG", "$name $this")
}


//fun Snackbar.applyTheme():Snackbar {
//    this.setBackgroundTint(ContextCompat.getColor(this.context, com.imagine.jordanpass.tools.R.color.active_marker))
//    this.setTextColor(Color.WHITE)
//
//    return this
//}
