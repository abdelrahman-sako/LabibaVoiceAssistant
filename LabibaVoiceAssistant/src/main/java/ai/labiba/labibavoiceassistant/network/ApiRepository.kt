package ai.labiba.labibavoiceassistant.network

import ai.labiba.labibavoiceassistant.enums.LabibaLanguages
import ai.labiba.labibavoiceassistant.models.Messaging
import ai.labiba.labibavoiceassistant.models.ReportRequestBody
import ai.labiba.labibavoiceassistant.other.Constants
import ai.labiba.labibavoiceassistant.sdkSetupClasses.LabibaVA
import ai.labiba.labibavoiceassistant.sdkSetupClasses.LabibaVAInternal

class ApiRepository(private val apiInterface: LabibaApiRetrofitInterface) {

    suspend fun sendMessage(data: Messaging.Request) = apiInterface.message(data)

    suspend fun getTextToSpeech(text:String,language: LabibaLanguages, isSSML:String = "false") = apiInterface.textToSpeech(text = text,LabibaVAInternal.labibaVaTheme.voice.getVoiceBasedOnLanguage(language),language.getTTsCode(),isSSML = isSSML)

    suspend fun reportBotResponse(requestBody: ReportRequestBody) = apiInterface.reportBotResponse(requestBody = requestBody)

}