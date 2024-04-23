package ai.labiba.labibavoiceassistant.interfaces

import android.view.View

interface LabibaUserChatInjectionCallbackInterface {

    fun injectCustomView(customView: View)

    fun injectTyping()

    fun clearChat()

    fun clearTypingAndChoices()

    fun addGifBackground(url:String,elevation:Float, loop:Boolean)

    fun sendMessage(message: String, showMessageInChat:Boolean)

}