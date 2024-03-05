package ai.labiba.labibavoiceassistant.adapter.chatAdapter.callbackInterfaces

import android.view.View

interface LabibaUserChatInjectionCallbackInterface {

    fun injectCustomView(customView: View)

    fun injectTyping()

    fun clearChat()

    fun clearTypingAndChoices()

}