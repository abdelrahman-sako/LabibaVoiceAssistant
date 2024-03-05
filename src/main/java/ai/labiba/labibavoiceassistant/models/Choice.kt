package ai.labiba.labibavoiceassistant.models

import com.google.gson.annotations.SerializedName

data class Choice(
    @SerializedName("content_type")
    val contentType: String,
    @SerializedName("image_url")
    val imageUrl: Any,
    @SerializedName("payload")
    val payload: String?,
    @SerializedName("title")
    val title: String
)
