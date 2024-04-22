package ai.labiba.labibavoiceassistant.utils.ext

import android.graphics.Color
import android.view.Window
import android.view.WindowManager
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment

fun Fragment.changeStatusBarColor(colorInHex: String?) {

    try {
        val window: Window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        val decorView = window.decorView
        val wic = WindowInsetsControllerCompat(window, decorView)
        wic.isAppearanceLightStatusBars = true // true or false as desired.


        window.statusBarColor = Color.parseColor(colorInHex)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Fragment.getCurrentStatusBarColor(): String? {

    return try {
        val window: Window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        val decorView = window.decorView
        val wic = WindowInsetsControllerCompat(window, decorView)
        wic.isAppearanceLightStatusBars = true // true or false as desired.

        // Convert the integer value to hexadecimal
        return "#"+Integer.toHexString(window.statusBarColor)

    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

}
