package ai.labiba.labibavoiceassistant.utils.ext

import android.graphics.Color
import android.view.Window
import android.view.WindowManager
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment

fun Fragment.changeStatusBarColor(colorInHex: String?) {

    colorInHex?.logd("LabibaVA status Color")
    try {
        val colorHex = if(colorInHex == "#0"){
            "#00000000"
        }else{
            colorInHex
        }

        val window: Window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        val decorView = window.decorView
        val wic = WindowInsetsControllerCompat(window, decorView)
        wic.isAppearanceLightStatusBars = true // true or false as desired.


        window.statusBarColor = Color.parseColor(colorHex)
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
