package ai.labiba.labibavoiceassistant.interfaces

import ai.labiba.labibavoiceassistant.enums.LabibaLanguages
import android.view.View

interface LabibaUserChatInjectionCallbackInterface {

    fun injectCustomView(customView: View)

    fun injectTyping()

    fun clearChat()

    fun clearTypingAndChoices()

    fun addGifBackground(url:String,elevation:Float, loop:Boolean)

    fun sendMessage(message: String, showMessageInChat:Boolean)

    fun addTTSMessageListToQueue(messageList: List<String>, language: LabibaLanguages,skipDuplicate:Boolean = false)
}