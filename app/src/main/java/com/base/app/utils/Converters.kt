package com.base.app.utils

import com.squareup.moshi.Moshi
import com.base.app.model.ErrorResponse
import com.base.app.shared.Constants.DoubleZeroPadding
import retrofit2.HttpException

fun timerConverter(timeInMillis: Long): String {
    val timeInSec = timeInMillis / 1000
    val hr = timeInSec / 60
    val sec = timeInSec % 60
    return "${DoubleZeroPadding.format(hr)}:${DoubleZeroPadding.format(sec)}"
}

fun responseConverter(throwable: HttpException): ErrorResponse {
    return try {
        throwable.response()?.errorBody()?.source()?.let {
            Moshi.Builder().build().adapter(ErrorResponse::class.java).fromJson(it)
        }?:ErrorResponse()
    } catch (e: Exception) {
        ErrorResponse()
    }
}