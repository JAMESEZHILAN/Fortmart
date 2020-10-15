package com.fortmart.customer.data.network

import com.fortmart.customer.model.request.NewPassword
import com.fortmart.customer.model.request.OTP
import com.fortmart.customer.model.request.Password
import com.fortmart.customer.model.response.LoginDetails
import com.fortmart.customer.model.response.OtpStatus
import com.fortmart.customer.model.entity.UserDetails
import com.fortmart.customer.shared.Constants
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