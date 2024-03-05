package ai.labiba.labibavoiceassistant.utils.ext

import android.util.Log
import androidx.lifecycle.ViewModel

fun ViewModel.handleErrors(errorString: String?): String {
    if (errorString != null && errorString == "Empty response") {
        Log.e("ViewModelsLog", "handleErrors: $errorString")
        return errorString
    }
    Log.e("ViewModelsLog", "handleErrors: $errorString")
    return errorString ?: ""

}