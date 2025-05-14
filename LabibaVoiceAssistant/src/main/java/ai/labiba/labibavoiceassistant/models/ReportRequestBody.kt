package ai.labiba.labibavoiceassistant.models


import com.google.gson.annotations.SerializedName

data class ReportRequestBody(
    @SerializedName("reason")
    val reason: String?,
    @SerializedName("report")
    val report: String?
)