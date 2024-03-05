package ai.labiba.labibavoiceassistant.network

import ai.labiba.labibavoiceassistant.sdkSetupClasses.LabibaVA
import ai.labiba.labibavoiceassistant.models.Messaging
import ai.labiba.labibavoiceassistant.models.TTSResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

interface LabibaApiRetrofitInterface {

    @POST
    suspend fun message(
        @Body data: Messaging.Request,
        @Url url: String = LabibaVA.urls.generalBaseUrl + LabibaVA.urls.messagingPath,
        @Header("Content-Type") header: String = "application/json"
    ): Response<Messaging.Response>

    @POST
    @FormUrlEncoded
    suspend fun textToSpeech(
        @Field("text") text:String,
        @Field("voicename") voice:String,
        @Field("language") language:String,
        @Url url: String = LabibaVA.urls.voiceBaseUrl + LabibaVA.urls.voicePath,
        @Field("isSSML") isSSML:String = "false",
        @Field("clientid") id:String = "0",
    ): Response<TTSResponse>


}