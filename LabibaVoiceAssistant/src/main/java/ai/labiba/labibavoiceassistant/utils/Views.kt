package ai.labiba.labibavoiceassistant.utils

import android.app.Activity
import android.graphics.Insets
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.WindowMetrics
import android.view.animation.AnimationUtils

object Views {

    /**
     * Hides the status bar and navigation bar
     * @param hide True: hide systemUI, False: show systemUI
     * @param activity The app activity
     * */
    fun showOrHideSystemUi(hide: Boolean, activity: Activity) {
        if (hide) {
            activity.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        } else {
            activity.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
    }


    /**
     * Get Screen Width
     * @param activity The Activity wants to get width for it
     * * @return Screen width pixel
     * */
    fun getScreenWidth(activity: Activity): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics: WindowMetrics = activity.windowManager.currentWindowMetrics
            val insets: Insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.width() - insets.left - insets.right
        } else {
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }

    }

    /**
     * Get Screen height
     * @param activity The Activity wants to get height for it
     * * @return Screen height pixel
     * */
    fun getScreenHeight(activity: Activity): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics: WindowMetrics = activity.windowManager.currentWindowMetrics
            val insets: Insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.height() - insets.top - insets.bottom
        } else {
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.heightPixels
        }

    }


    /**
     * Get Item Width for Dynamic width
     * @param screenWidth The Width of screen have Items in it
     * @param numberOfItem The Number of Items want to add in screen
     * @param marginAroundItems The margin around container have items
     * @param marginBetweenItem The margin between items together
     * @return Item width pixel
     * */
    fun getItemWidth(
        screenWidth: Int,
        numberOfItem: Int,
        marginAroundItems: Int,
        marginBetweenItem: Int
    ): Int {
        val viewItems = screenWidth - (marginAroundItems * 2)
        val withoutItemMargin = viewItems - ((numberOfItem - 1) * marginBetweenItem)

        return withoutItemMargin / numberOfItem
    }


//    fun hideBottomNavigation(splash : Int, navHostFragment: NavHostFragment, view: BottomNavigationView){
//        navHostFragment.navController.addOnDestinationChangedListener{_,destination,_ ->
//
//            when(destination.id){
//                splash -> {
//                    view.visibility = View.GONE
//                }
//                else -> {
//                    view.visibility = View.VISIBLE
//                }
//            }
//
//        }
//    }

}