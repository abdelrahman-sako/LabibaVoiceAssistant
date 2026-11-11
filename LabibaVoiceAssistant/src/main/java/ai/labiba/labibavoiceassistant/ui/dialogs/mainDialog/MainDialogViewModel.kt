package ai.labiba.labibavoiceassistant.ui.dialogs.mainDialog

import ai.labiba.labibavoiceassistant.enums.LabibaLanguages
import ai.labiba.labibavoiceassistant.enums.MessageTypes
import ai.labiba.labibavoiceassistant.models.Messaging
import ai.labiba.labibavoiceassistant.models.TTSResponse
import ai.labiba.labibavoiceassistant.network.ApiRepository
import ai.labiba.labibavoiceassistant.network.RetrofitClient
import ai.labiba.labibavoiceassistant.other.Constants
import ai.labiba.labibavoiceassistant.utils.apiHandleResponse.HandleResults
import ai.labiba.labibavoiceassistant.utils.apiHandleResponse.Resource
import ai.labiba.labibavoiceassistant.utils.ext.logd
import androidx.core.text.HtmlCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainDialogViewModel : ViewModel() {
    private val apiRepository = ApiRepository(RetrofitClient.getApiInterface())

    private val _messaging = MutableLiveData<Resource<Messaging.Response>?>()
    val messaging: LiveData<Resource<Messaging.Response>?>
        get() = _messaging

    private val _speechAudioLink = MutableLiveData<Resource<TTSResponse>?>()
    val speechAudioLink: LiveData<Resource<TTSResponse>?>
        get() = _speechAudioLink

    private var mTTSJob: Job? = null
    private var messageJob: Job? = null

    fun requestMessage(data: Messaging.Request) {
        messageJob = viewModelScope.launch(Dispatchers.IO) {
            _messaging.postValue(Resource.Loading())

            val e: String? = try {
                val call = apiRepository.sendMessage(data)

                if (call.body() != null && call.isSuccessful) {

                    _messaging.postValue(
                        HandleResults<Messaging.Response>().handle(call)
                    )


                    delay(300)
                    _messaging.postValue(null)

                } else {
                    call.message()
                }

                return@launch
            } catch (e: Exception) {
                e.printStackTrace()
                e.message
            }
            _messaging.postValue(Resource.Error(e ?: ""))
        }
    }

    fun requestTextToSpeech(text: String,language:LabibaLanguages,ssml:Boolean = false) {
        val cleanText = if (!ssml) {
            HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY).toString().trim()
        } else {
            text
        }
        mTTSJob = viewModelScope.launch(Dispatchers.IO) {
            _speechAudioLink.postValue(Resource.Loading())

            try {
                val call = apiRepository.getTextToSpeech(cleanText,language,ssml.toString())

                if (call.body() != null && call.isSuccessful && call.body()?.status != "error") {

                    _speechAudioLink.postValue(
                        HandleResults<TTSResponse>().handle(call)
                    )

                    delay(300)
                    _speechAudioLink.postValue(null)

                } else {
                    _speechAudioLink.postValue(Resource.Error(call.message()))
                }


            } catch (e: Exception) {
                e.printStackTrace()
                _speechAudioLink.postValue(Resource.Error(e.message?:""))
            }
        }
    }

    /**
     * Start conversation with the bot
     * */
    fun startConversation(senderID: String) {
        val type = MessageTypes.TEXT
        val message = "CONVERSATION-RELOAD"
        requestMessage(
            type.convertToModel(
                message, senderID, Constants.getRecipientId()
            )
        )
    }

    /**
     * Stop TTS and message API requests
     * */
    fun stopRequests() {
        mTTSJob?.cancel("Manual Cancel")
        messageJob?.cancel("Manual Cancel")
    }


}