package ai.labiba.labibavoiceassistant.models

import com.google.gson.annotations.SerializedName

data class Card(
    @SerializedName("buttons")
    val buttons: List<Button>?,
    @SerializedName("default_action")
    val defaultAction: Any,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("subtitle")
    val subtitle: String?,
    @SerializedName("title")
    val title: String
) {
    var imageAspectRatio: String = ""
    data class Button(
        @SerializedName("payload")
        val payload: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("type")
        val type: String,
        @SerializedName("url")
        val url: Any,
        @SerializedName("webview_height_ratio")
        val webViewHeightRatio: Any
    )
}
