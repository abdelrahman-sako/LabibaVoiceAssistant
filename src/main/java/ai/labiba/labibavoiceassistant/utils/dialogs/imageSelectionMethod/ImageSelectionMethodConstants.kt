package ai.labiba.labibavoiceassistant.utils.dialogs.imageSelectionMethod

import android.content.Context
import android.graphics.Color
import androidx.annotation.ColorInt

object ImageSelectionMethodConstants {

    internal lateinit var fileProviderAuthority: String
    internal lateinit var applicationID: String

    fun init(context: Context?) {
        fileProviderAuthority = "${context?.packageName}.com.imagine.tools.file.provider"
        applicationID = context?.packageName ?: ""
    }

    // -colors-
    @ColorInt var backGroundColor: Int = Color.parseColor("#ffffff")
    @ColorInt var cameraButtonTextColor: Int = Color.parseColor("#000000")
    @ColorInt var galleryButtonTextColor: Int = Color.parseColor("#000000")
    @ColorInt var separatingDividerLineColor: Int = Color.parseColor("#000000")

    // -strings-
    var cameraString = "Camera"
    var galleryString = "Gallery"
    var permissionDeniedDialogMessage =
        "Permission was denied for this action, you can grant permission to use this action from the settings page. \nopen settings page?"
    var permissionDeniedDialogTitle = "Permission denied previously"

    // -variables-
    var compressUntilBytes = 3670016L
}