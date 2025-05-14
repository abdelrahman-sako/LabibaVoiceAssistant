package ai.labiba.labibavoiceassistant.ui.dialogs.reportDialog

import ai.labiba.labibavoiceassistant.models.ReportRequestBody
import ai.labiba.labibavoiceassistant.models.ReportResponse
import ai.labiba.labibavoiceassistant.network.ApiRepository
import ai.labiba.labibavoiceassistant.network.RetrofitClient
import ai.labiba.labibavoiceassistant.utils.apiHandleResponse.HandleResults
import ai.labiba.labibavoiceassistant.utils.apiHandleResponse.Resource
import ai.labiba.labibavoiceassistant.utils.ext.handleErrors
import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ReportDialogViewModel : ViewModel() {
    private val apiRepository = ApiRepository(RetrofitClient.getApiInterface())

    private val _reportResult = MutableLiveData<Resource<ReportResponse>>()
    val reportResult: LiveData<Resource<ReportResponse>>
        get() = _reportResult

    fun submitReport(reason: String,report:String?) {

        viewModelScope.launch(Dispatchers.IO) {
            _reportResult.postValue(Resource.Loading())
            try {
                val response = apiRepository.reportBotResponse(ReportRequestBody(reason,report?:"NA"))

                if (response.body() == null) {
                    _reportResult.postValue(Resource.Error(handleErrors("Empty response")))
                } else if (!response.isSuccessful) {
                    _reportResult.postValue(Resource.Error(handleErrors(response.message())))
                } else if(response.body()?.status == false){
                    _reportResult.postValue(Resource.Error(handleErrors(response.body()?.errorMessage)))
                }
                else {
                    _reportResult.postValue(
                        HandleResults<ReportResponse>().handle(
                            response
                        )
                    )


                    //delay(200)
                    //_reportResult.postValue(null)

                }
                return@launch
            } catch (e: NetworkErrorException) {
                _reportResult.postValue(Resource.Error(handleErrors(e.message)))
            } catch (e: HttpException) {
                _reportResult.postValue(Resource.Error(handleErrors(e.message)))
            } catch (e: IOException) {
                _reportResult.postValue(Resource.Error(handleErrors(e.message)))
            } catch (e: Throwable) {
                _reportResult.postValue(Resource.Error(handleErrors(e.message)))
            }


        }


    }

}