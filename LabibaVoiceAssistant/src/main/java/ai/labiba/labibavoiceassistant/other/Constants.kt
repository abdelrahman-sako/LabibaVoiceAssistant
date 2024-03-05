package ai.labiba.labibavoiceassistant.other

import ai.labiba.labibavoiceassistant.enums.LabibaLanguages

object Constants {

    const val ConversationReload = "CONVERSATION-RELOAD"
    const val GetStarted = "get started"

    internal val recipientIds = ArrayList<Pair<String, LabibaLanguages>>()
    internal var languageName: LabibaLanguages = LabibaLanguages.ENGLISH
    internal var voiceLanguage: String = "en-US"

    //SharedPreferences
    const val SHARED_MAIN = "SHARED_MAIN"
    const val SHARED_SENDER_ID = "SHARED_SENDER_ID"

    fun getRecipientId(): String =
        if (recipientIds.size == 1) recipientIds[0].first
        else if (recipientIds.size > 1) recipientIds.filter { it.second == languageName }[0].first
        else ""


    const val TAG_LABIBA_DEBUG = "LABIBA_D"
    const val TAG_LABIBA_INFORMATION = "LABIBA_I"
    const val TAG_LABIBA_ERROR = "LABIBA_E"
    const val TAG_LABIBA_WARNING = "LABIBA_W"

}