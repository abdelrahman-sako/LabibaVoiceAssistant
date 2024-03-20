package ai.labiba.labibavoiceassistant.utils.dialogs

import ai.labiba.labibavoiceassistant.R
import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import androidx.annotation.ColorInt
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class CustomBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var loadingDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MyBottomSheetDialogTheme)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //change background color to white (changing xml in tint does not work on api 21)
        if(view.background!=null){
            view.background.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                Color.parseColor("#ffffff"),
                BlendModeCompat.SRC_ATOP)
        }

    }

    fun marginTop(margin:Int){
        val dialog = (dialog as BottomSheetDialog)
        dialog.behavior.apply {
            isFitToContents = false
            expandedOffset = margin
            state = BottomSheetBehavior.STATE_EXPANDED
        }
    }



    fun showLoadingDialog(isCancelable: Boolean = true,@ColorInt loadingProgressBarColor:Int = Color.parseColor("#000000")) {
        loadingDialog = Dialog(requireContext())
        loadingDialog.setCancelable(isCancelable)
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        loadingDialog.setContentView(R.layout.layout_dialog_loading)
        loadingDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        loadingDialog.findViewById<ProgressBar>(R.id.custom_dialog_progress_bar).indeterminateTintList = ColorStateList.valueOf(loadingProgressBarColor)
        loadingDialog.show()
    }

    fun dismissLoadingDialog() {
        if (this::loadingDialog.isInitialized)
            loadingDialog.dismiss()
    }

    fun alwaysExpanded(){
        val dialog = (dialog as BottomSheetDialog)

        dialog.setOnShowListener {
            dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED

            dialog.behavior.skipCollapsed = false
            dialog.behavior.hideFriction = 0.2f
        }
    }



}