package com.fortmart.customer.data.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.fortmart.customer.enums.RetrofitStatus
import com.fortmart.customer.model.Error
import com.fortmart.customer.model.ErrorResponse
import com.fortmart.customer.utils.responseConverter
import retrofit2.HttpException
import java.net.SocketTimeoutException

suspend fun <T> handleApiCall(
    status: MutableLiveData<RetrofitStatus>?,
    apiCall: suspend () -> T?
): ResponseWrapper<T?> {
    status?.postValue(RetrofitStatus.IN_PROGRESS)
    val result =
        try {
            ResponseWrapper.Success(apiCall.invoke())
        } catch (e: Exception) {
            Log.i("qwerty","API failure $e")
//            status.postValue(RetrofitStatus.FAILED)
            when (e) {
                is HttpException -> {
                    val responseCode = e.code()
                    val errorResponse = responseConverter(e)
                    ResponseWrapper.Failure(responseCode, errorResponse)
                }

                is SocketTimeoutException -> ResponseWrapper.Failure(
                    errorResponse = ErrorResponse(
                        Error(message = "Connection timeout")
                    )
                )

                is NullPointerException -> {
                    ResponseWrapper.Failure(204, ErrorResponse())
                }
                else -> {
                    ResponseWrapper.Failure()
                }
            }
        }
//    if (status.value == RetrofitStatus.IN_PROGRESS)
    status?.postValue(RetrofitStatus.COMPLETED)
    return result
}