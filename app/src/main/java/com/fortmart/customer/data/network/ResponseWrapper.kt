package com.fortmart.customer.data.network

import com.fortmart.customer.model.Error
import com.fortmart.customer.model.ErrorResponse

sealed class ResponseWrapper<out T> {
    data class Success<out T>(val dataResponse: T) : ResponseWrapper<T>()
    data class Failure(
        val responseCode: Int = 0, val errorResponse: ErrorResponse = ErrorResponse(
            Error(message = "Something went wrong!")
        )
    ) :
        ResponseWrapper<Nothing>()

    object Error : ResponseWrapper<Nothing>()
}