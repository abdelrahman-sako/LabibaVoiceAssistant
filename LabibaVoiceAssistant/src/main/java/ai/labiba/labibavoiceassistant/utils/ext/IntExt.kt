package ai.labiba.labibavoiceassistant.utils.ext

import android.graphics.Color


fun Int.setColorAlpha(alpha: Int): Int{
    val red = Color.red(this)
    val green = Color.green(this)
    val blue = Color.blue(this)
    return Color.argb(alpha, red, green, blue)
}