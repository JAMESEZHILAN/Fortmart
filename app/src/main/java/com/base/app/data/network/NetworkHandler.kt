package com.base.app.data.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.base.app.enums.RetrofitStatus
import com.base.app.model.Error
import com.base.app.model.ErrorResponse
import com.base.app.utils.responseConverter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
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