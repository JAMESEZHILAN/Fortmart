package com.base.app.data.network

import com.base.app.model.request.NewPassword
import com.base.app.model.request.OTP
import com.base.app.model.request.Password
import com.base.app.model.response.LoginDetails
import com.base.app.model.response.OtpStatus
import com.base.app.model.entity.UserDetails
import com.base.app.shared.Constants
import retrofit2.http.*

interface LoginApiService {

    @GET("")
    suspend fun login(
        @Query(Constants.PHONE) phone: Long = 0,
        @Query(Constants.EMAIL) email: String = ""
    ): LoginDetails

    @GET("")
    suspend fun sendOtp(@Query(Constants.PHONE) phone: Long): OtpStatus

    @POST("")
    suspend fun verifyOtp(@Body otp: OTP): UserDetails

    @POST("")
    suspend fun createPassword(@Body newPassword: NewPassword): UserDetails

    @POST("")
    suspend fun verifyPassword(@Body password: Password): UserDetails

    @POST("")
    suspend fun logout()
}