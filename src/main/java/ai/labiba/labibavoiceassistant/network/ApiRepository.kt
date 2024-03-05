package ai.labiba.labibavoiceassistant.network

import ai.labiba.labibavoiceassistant.enums.LabibaLanguages
import ai.labiba.labibavoiceassistant.models.Messaging
import ai.labiba.labibavoiceassistant.other.Constants
import ai.labiba.labibavoiceassistant.sdkSetupClasses.LabibaVA

class ApiRepository(private val apiInterface: LabibaApiRetrofitInterface) {

    suspend fun sendMessage(data: Messaging.Request) = apiInterface.message(data)

    suspend fun getTextToSpeech(text:String,language: LabibaLanguages) = apiInterface.textToSpeech(text = text,LabibaVA.voice.getVoiceBasedOnLanguage(language),language.getTTsCode())

}