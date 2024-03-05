package ai.labiba.labibavoiceassistant.utils.dialogs.imageSelectionMethod

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File

class ImageSelectionMethodViewModel:ViewModel() {

    private val _imageName = MutableLiveData<String?>()
    val imageName:LiveData<String?>
    get() = _imageName



    fun setImageName(imageName:String){

        _imageName.postValue(imageName)

    }


    @SuppressLint("Range")
    fun setImageName(uri: Uri,context: Context) {

        var result: String? = null
        if (uri.scheme.equals("content")) {
            val cursor: Cursor? =
                context.contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    if (cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME) != -1) {
                        result =
                            cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (cursor != null) {
                    cursor.close()
                }
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf(File.separator)
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }
        _imageName.postValue(result)
    }


}