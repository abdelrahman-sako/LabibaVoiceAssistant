package ai.labiba.labibavoiceassistant.utils.apiHandleResponse

import retrofit2.Response

class HandleResults<T> {

    fun handle(response: Response<T>): Resource<T> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}