package ai.labiba.labibavoiceassistant.models

import com.google.gson.annotations.SerializedName

data class TTSResponse(@SerializedName("status") val status: String, @SerializedName("file") val audioUrl: String)