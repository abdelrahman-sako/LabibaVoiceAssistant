package ai.labiba.labibavoiceassistant.models

import ai.labiba.labibavoiceassistant.enums.MediaType
import ai.labiba.labibavoiceassistant.sdkSetupClasses.LabibaVAInternal
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.json.JSONObject
import java.util.*


class Messaging {

    class Response : ArrayList<Response.ResponseItem>() {
        data class ResponseItem(
            @SerializedName("message")
            val message: Message,
            @SerializedName("messaging_type")
            val messagingType: String,
            @SerializedName("recipient")
            val recipient: Recipient
        ) {
            data class Message(
                @SerializedName("attachment")
                val attachment: Attachment? = null,
                @SerializedName("metadata")
                val metadata: String,
                @SerializedName("quick_replies")
                val quickReplies: List<Choice>? = null,
                @SerializedName("text")
                val text: String? = null
            ) {

                data class Attachment(
                    @SerializedName("payload")
                    val payload: Payload? = null,
                    @SerializedName("type")
                    val type: String
                ) {
                    data class Payload(
                        @SerializedName("elements")
                        val elements: List<Card> = listOf(),
                        @SerializedName("image_aspect_ratio")
                        val imageAspectRatio: String,
                        @SerializedName("sharable")
                        val sharable: Boolean,
                        @SerializedName("template_type")
                        val templateType: String,
                        @SerializedName("url")
                        val url: String
                    )
                }
            }

            data class Recipient(
                @SerializedName("id")
                val id: String
            )
        }
    }

    data class Request(
        @SerializedName("entry")
        val entry: List<Entry>,
        @SerializedName("object")
        val objectX: String = "page"
    ) {
        data class Entry(
            @SerializedName("messaging")
            val messaging: List<Messaging>,
            @SerializedName("id")
            val id: String = UUID.randomUUID().toString(),
            @SerializedName("time")
            val time: Long = System.currentTimeMillis()
        ) {
            data class Messaging(
                @SerializedName("message")
                val message: Message?,
                @SerializedName("sender")
                val sender: Sender,
                @SerializedName("recipient")
                val recipient: Recipient,
                @SerializedName("referral")
                val referral: Referral,
                @SerializedName("postBack")
                val postBack: PostBack? = null,
                @SerializedName("Id")
                val id: String = UUID.randomUUID().toString(),
                @SerializedName("timestamp")
                val timestamp: String = System.currentTimeMillis().toString()
            ) {
                data class Message(
                    @SerializedName("text")
                    var text: String? = null,
                    @SerializedName("attachments")
                    var attachments: Attachments? = null,
                    @SerializedName("postback")
                    var postback: Postback? = null
                ) {
                    data class Postback(val temp:String)

                    data class Attachments(
                        @SerializedName("payload")
                        var payload: Payload? = null,
                        @SerializedName("type")
                        var type: String? = null

                    ) {
                        data class Payload(
                            @SerializedName("coordinates")
                            var coordinates: Coordinates? = null,
                            @SerializedName("url")
                            var url: String? = null
                        ) {
                            data class Coordinates(
                                @SerializedName("lat")
                                var Lat: Double? = null,
                                @SerializedName("long")
                                var Long: Double? = null
                            )

                        }
                    }
                }

                data class Recipient(
                    @SerializedName("id")
                    var id: String
                )

                data class Sender(
                    @SerializedName("id")
                    var id: String
                )

                data class PostBack(
                    @SerializedName("payload")
                    var payload: String? = null,
                    @SerializedName("url")
                    var url: String? = null
                )

                data class Referral(
                    @SerializedName("ref")
                    var ref: String,
                    @SerializedName("source")
                    val source: String = "",
                    @SerializedName("type")
                    val type: String = ""
                )
            }
        }
    }

    fun createTextMessage(message: String, senderId: String, recipientId: String): Request {
        val messageObj = Request.Entry.Messaging.Message(message)
        val senderObj = Request.Entry.Messaging.Sender(senderId)
        val recipientObj = Request.Entry.Messaging.Recipient(recipientId)

        val refMap = LabibaVAInternal.flags
        refMap.put("source", "mobile")
        refMap.put("GoogleServices", true)



        val referralObj = Request.Entry.Messaging.Referral(convertFlagMapToRefJson(refMap))
        LabibaVAInternal.flags.clear()


        val messages = Request.Entry.Messaging(
            message = messageObj,
            sender = senderObj,
            recipient = recipientObj,
            referral = referralObj
        )
        val entry = Request.Entry(listOf(messages))
        return Request(listOf(entry))
    }

    fun createCardMessage(payload: String, senderId: String, recipientId: String): Request {
        val postback = Request.Entry.Messaging.PostBack(payload)
        val senderObj = Request.Entry.Messaging.Sender(senderId)
        val recipientObj = Request.Entry.Messaging.Recipient(recipientId)


        val refMap = LabibaVAInternal.flags
        refMap.put("source", "mobile")
        refMap.put("GoogleServices", true)

        val referralObj = Request.Entry.Messaging.Referral(convertFlagMapToRefJson(refMap))
        LabibaVAInternal.flags.clear()

        val messages = Request.Entry.Messaging(
            message = null,
            sender = senderObj,
            recipient = recipientObj,
            referral = referralObj,
            postBack = postback
        )
        val entry = Request.Entry(listOf(messages))
        return Request(listOf(entry))
    }

    fun createMediaMessage(
        url: String,
        type: MediaType,
        senderId: String,
        recipientId: String
    ): Request {

        val message = Request.Entry.Messaging.Message(
            attachments = Request.Entry.Messaging.Message.Attachments(
                payload = Request.Entry.Messaging.Message.Attachments.Payload(
                    coordinates = null,
                    url = url
                ),
                type = type.type
            )
        )

        val senderObj = Request.Entry.Messaging.Sender(senderId)
        val recipientObj = Request.Entry.Messaging.Recipient(recipientId)

        val refMap = LabibaVAInternal.flags
        refMap.put("source", "mobile")
        refMap.put("GoogleServices", true)

        val referralObj = Request.Entry.Messaging.Referral(convertFlagMapToRefJson(refMap))
        LabibaVAInternal.flags.clear()

        val messages = Request.Entry.Messaging(
            message = message,
            sender = senderObj,
            recipient = recipientObj,
            referral = referralObj,
        )
        val entry = Request.Entry(listOf(messages))
        return Request(listOf(entry))
    }

    fun createLocationMessage(
        lat: Double,
        long: Double,
        senderId: String,
        recipientId: String
    ): Request {
        //attachments
        val message = Request.Entry.Messaging.Message(
            attachments = Request.Entry.Messaging.Message.Attachments(
                Request.Entry.Messaging.Message.Attachments.Payload(
                    Request.Entry.Messaging.Message.Attachments.Payload.Coordinates(
                        Lat = lat,
                        Long = long
                    )
                )
            )
        )
        val senderObj = Request.Entry.Messaging.Sender(senderId)
        val recipientObj = Request.Entry.Messaging.Recipient(recipientId)

        val refMap = LabibaVAInternal.flags
        refMap.put("source", "mobile")
        refMap.put("GoogleServices", true)

        val referralObj = Request.Entry.Messaging.Referral(convertFlagMapToRefJson(refMap))
        LabibaVAInternal.flags.clear()

        val messages = Request.Entry.Messaging(
            message = message,
            sender = senderObj,
            recipient = recipientObj,
            referral = referralObj,
        )
        val entry = Request.Entry(listOf(messages))
        return Request(listOf(entry))
    }

    private fun convertFlagMapToRefJson(flags: MutableMap<String, Any>): String {
        val gson = Gson()
        val jsonList = flags.entries.map { entry ->
            when (val value = entry.value) {
                is String -> gson.toJsonTree(mapOf(entry.key to value))
                is Boolean -> gson.toJsonTree(mapOf(entry.key to value))
                is Int -> gson.toJsonTree(mapOf(entry.key to value))
                is Float -> gson.toJsonTree(mapOf(entry.key to value))
                is Double -> gson.toJsonTree(mapOf(entry.key to value))
                else -> {
                    try {
                        gson.toJsonTree(mapOf(entry.key to value))
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }
            }
        }.filterNotNull()

        return gson.toJson(jsonList)
    }
}