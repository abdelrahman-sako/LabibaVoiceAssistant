package ai.labiba.labibavoiceassistant.sdkSetupClasses

import ai.labiba.labibavoiceassistant.enums.ChatType
import ai.labiba.labibavoiceassistant.models.Chat
import ai.labiba.labibavoiceassistant.models.Messaging
import org.json.JSONObject

internal object LabibaApiResponseHandle {

    fun handleResponse(
        response: Messaging.Response,
        onComplete: (List<Chat>) -> Unit,
        onSendVoice: ((String) -> Unit)
    ) {

        val dataList: ArrayList<Chat> = arrayListOf()

        response.forEachIndexed { index, entity ->
            val textMessage = entity.message.text
            val choiceMessage = entity.message.quickReplies
            val attachmentMessage = entity.message.attachment


            //--------Attachment----------
            if (attachmentMessage != null) {

                when (attachmentMessage.type.lowercase()) {
                    "template" -> {

                        if (attachmentMessage.payload != null) {
                            if (attachmentMessage.payload.elements.isNotEmpty()) {

                                //filter deeplink items
                                //deeplink items always have only one card and one button of type "create_post",
                                if (attachmentMessage.payload.elements.getOrNull(0)?.buttons?.getOrNull(0)?.type == "create_post") {
                                    val payload =
                                        attachmentMessage.payload.elements[0].buttons?.get(0)?.payload
                                    try {
                                        //get type and data from payload json string
                                        val payloadJsonObject = JSONObject(payload ?: "")
                                        val type = payloadJsonObject.getString("type")
                                        val data =
                                            payloadJsonObject.getJSONObject("data").toString()

                                        //Send empty voice audio to fill the TTS queue
                                        handleTTSVoiceMessage("\n",onSendVoice)

                                        dataList.add(
                                            Chat().setData(
                                                chatType = ChatType.CREATE_POST,
                                                createPostPayload = Pair(type, data)
                                            )
                                        )
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }else{
                                    //Send empty voice audio to fill the TTS queue
                                    handleTTSVoiceMessage("\n",onSendVoice)

                                    dataList.add(
                                        Chat().setData(
                                            chatType = ChatType.CARDS,
                                            cards = attachmentMessage.payload.elements
                                        )
                                    )
                                }


                            }
                        }
                    }

                    "image" -> {
                        //Send empty voice audio to fill the TTS queue
                        handleTTSVoiceMessage("\n",onSendVoice)

                        dataList.add(
                            Chat().setData(
                                chatType = ChatType.MEDIA_IMAGE,
                                mediaUrl = attachmentMessage.payload?.url
                            )
                        )
                    }

                    "video" -> {
                        //Send empty voice audio to fill the TTS queue
                        handleTTSVoiceMessage("\n",onSendVoice)

                        dataList.add(
                            Chat().setData(
                                chatType = ChatType.MEDIA_VIDEO,
                                mediaUrl = attachmentMessage.payload?.url
                            )
                        )
                    }

                    "audio" -> {
                        //Send empty voice audio to fill the TTS queue
                        handleTTSVoiceMessage("\n",onSendVoice)

                        dataList.add(
                            Chat().setData(
                                chatType = ChatType.MEDIA_AUDIO,
                                mediaUrl = attachmentMessage.payload?.url
                            )
                        )
                    }

                }

            }
            //--------message and choice----------
            else if (!textMessage.isNullOrEmpty() && !choiceMessage.isNullOrEmpty()) {
                val text = handleTTSVoiceMessage(textMessage, onSendVoice)

                dataList.add(
                    Chat().setData(
                        ChatType.CHOICES,
                        textMessage = text,
                        choices = choiceMessage
                    )
                )

            }
            //--------message only----------
            else if (!textMessage.isNullOrEmpty()) {
                val text = handleTTSVoiceMessage(textMessage, onSendVoice)

                dataList.add(
                    Chat().setData(
                        chatType = ChatType.BOT_TEXT,
                        textMessage = text
                    )
                )

                if (!choiceMessage.isNullOrEmpty()) {

                    dataList.add(
                        Chat().setData(
                            chatType = ChatType.CHOICES,
                            choices = choiceMessage
                        )
                    )
                }

            }
            //--------choice only----------
            else if (!choiceMessage.isNullOrEmpty()) {
                //Send empty voice audio to fill the TTS queue
                handleTTSVoiceMessage("\n",onSendVoice)

//                    botChoicesResponse(choiceMessage)
                dataList.add(
                    Chat().setData(
                        chatType = ChatType.CHOICES,
                        choices = choiceMessage
                    )
                )

            }


        }
        onComplete.invoke(dataList)
    }

    private fun handleTTSVoiceMessage(textMessage: String, onSendVoice: (String) -> Unit): String {
        val messageSplitter = textMessage.split("@:@")
        var text = ""

        if (messageSplitter.size > 1) {
            onSendVoice(messageSplitter[1])
            text = messageSplitter[1]
        } else {
            onSendVoice(messageSplitter[0])
            text = messageSplitter[0]
        }

        return text
    }


}