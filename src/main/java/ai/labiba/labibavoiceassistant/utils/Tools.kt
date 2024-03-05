package ai.labiba.labibavoiceassistant.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult


object Tools {

    fun vibrate(activity: Activity,milis:Long = 100){


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val v = activity.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            v.vibrate(CombinedVibration.createParallel(VibrationEffect.createOneShot(milis,VibrationEffect.DEFAULT_AMPLITUDE)))
        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val v = activity.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            v.vibrate(VibrationEffect.createOneShot(milis,VibrationEffect.DEFAULT_AMPLITUDE))
        }else{
            val v = activity.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            v.vibrate(milis)
        }



    }

    /**
     * calculate and returns black or white color based on image color
     * @param image
     * @param context
     * @return color (black or white)
     * */

    suspend fun getColorBasedOnBackgroundImage(image:Any?,context: Context):Int{
        //get image as bitmap
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(image)
            .allowHardware(false) // Disable hardware bitmaps.
            .build()

        val result = (loader.execute(request) as SuccessResult).drawable
        val bitmap = (result as BitmapDrawable).bitmap

        //get average image color
        val bgColor = calculateAverageColorOfABitmap(bitmap,10)

        //get icon color based on background color (average image color)
        return getColorBasedOnBackgroundColor(bgColor)
    }

    /**
     *  calculates and returns black or white color based on background color
     *  @param color background color
     *  @return color (black or white)
     * */
     fun getColorBasedOnBackgroundColor(@ColorInt color: Int): Int  {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        val luminance = (0.2126 * red + 0.7152 * green + 0.0722 * blue) / 255
        return if (luminance > 0.5) Color.BLACK else Color.WHITE
    }

    /**
    pixelSpacing tells how many pixels to skip each pixel.
    If pixelSpacing > 1: the average color is an estimate, but higher values mean better performance
    If pixelSpacing == 1: the average color will be the real average
    If pixelSpacing < 1: the method will most likely crash (don't use values below 1)
    */
    fun calculateAverageColorOfABitmap(bitmap: Bitmap, pixelSpacing: Int): Int {
        var R = 0
        var G = 0
        var B = 0
        val height = bitmap.height
        val width = bitmap.width
        var n = 0
        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
        var i = 0
        while (i < pixels.size) {
            val color = pixels[i]
            R += Color.red(color)
            G += Color.green(color)
            B += Color.blue(color)
            n++
            i += pixelSpacing
        }
        return Color.rgb(R / n, G / n, B / n)
    }

    fun showKeyboard(activity: Activity,view: View){
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    fun hideKeyboard(activity: Activity,view: View){
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken,0)
    }

}