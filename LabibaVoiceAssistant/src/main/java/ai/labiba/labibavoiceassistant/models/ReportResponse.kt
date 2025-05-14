package ai.labiba.labibavoiceassistant.models


import com.google.gson.annotations.SerializedName

data class ReportResponse(
    @SerializedName("data")
    val `data`: String?, // Submitted Successfully
    @SerializedName("errorMessage")
    val errorMessage: String?,
    @SerializedName("status")
    val status: Boolean? // true
)