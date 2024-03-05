package ai.labiba.labibavoiceassistant.models

import ai.labiba.labibavoiceassistant.enums.ChatType
import android.graphics.Bitmap
import android.view.View

data class Chat(var type: ChatType = ChatType.NON, var timestamp: Long = System.currentTimeMillis()+ (1..99999999).random(), var text: String? = null,  var choices: List<Choice>? = null,  var cards: List<Card>? = null,  var mediaUrl: String? = null,  var image: Bitmap? = null,var scaleDown:Boolean = false,var customView: View? = null ) {

//    val type get() = _type
//    val timestamp get() = _timestamp
//    val text get() = _text
//    val choices get() = _choices
//    val cards get() = _cards
//    val mediaUrl get() = _mediaUrl
//    val image get() = _image

    fun setData(
        chatType: ChatType,
        textMessage: String? = null,
        choices: List<Choice>? = null,
        cards: List<Card>? = null,
        mediaUrl: String? = null,
        time: Long? = null,
        image: Bitmap? = null,
        customView: View? = null
    ): Chat {
        this.type = chatType
        this.timestamp = time ?: System.currentTimeMillis()
        this.text = textMessage
        this.choices = choices
        this.cards = cards
        this.mediaUrl = mediaUrl
        this.image = image
        this.customView = customView
        return this
    }
}