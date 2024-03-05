package ai.labiba.labibavoiceassistant.utils

import ai.labiba.labibavoiceassistant.other.Constants
import android.content.Context
import java.util.UUID

internal class SharedUtils(
    private val context: Context
) {

    private val sharedPreferences =
        context.getSharedPreferences(Constants.SHARED_MAIN, Context.MODE_PRIVATE)

    fun getSenderId(): String {
        var senderId = sharedPreferences.getString(Constants.SHARED_SENDER_ID, "") ?: ""
        if (senderId.isEmpty()) {
            senderId = UUID.randomUUID().toString()
            setSenderId(senderId)
        }
        return senderId
    }

    fun setSenderId(senderId: String) {
        val editor = sharedPreferences.edit()
        editor.putString(Constants.SHARED_SENDER_ID, senderId)
        editor.apply()
    }


}