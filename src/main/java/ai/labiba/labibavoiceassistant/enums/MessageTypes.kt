package ai.labiba.labibavoiceassistant.enums

import ai.labiba.labibavoiceassistant.models.Messaging
import ai.labiba.labibavoiceassistant.other.Constants


enum class MessageTypes {
    TEXT,
    CHOICE,
    CARD,
    MEDIA,
    LOCATION;

    fun convertToModel(message: String, senderId: String, recipientId: String = Constants.getRecipientId()): Messaging.Request {
        return when (this) {
            TEXT -> Messaging().createTextMessage(message, senderId, recipientId)
            CHOICE -> Messaging().createTextMessage(message, senderId, recipientId)
            CARD -> Messaging().createCardMessage(message, senderId, recipientId)
            else -> {
                Messaging().createTextMessage(message, senderId, recipientId)
            }
        }
    }

    fun convertToModel(
        url: String,
        mediaType: MediaType,
        senderId: String,
        recipientId: String
    ): Messaging.Request {
        return Messaging().createMediaMessage(url, mediaType, senderId, recipientId)
    }

    fun convertToModel(
        lat: Double,
        long: Double,
        senderId: String,
        recipientId: String
    ): Messaging.Request {
        return Messaging().createLocationMessage(lat, long, senderId, recipientId)
    }

}