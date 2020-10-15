package com.fortmart.customer.data.repository

import androidx.lifecycle.MutableLiveData
import com.fortmart.customer.data.dao.UserDao
import com.fortmart.customer.data.network.LoginApiService
import com.fortmart.customer.data.network.ResponseWrapper
import com.fortmart.customer.data.network.handleApiCall
import com.fortmart.customer.enums.RetrofitStatus
import com.fortmart.customer.model.request.OTP
import com.fortmart.customer.model.response.LoginDetails
import com.fortmart.customer.model.response.OtpStatus
import com.fortmart.customer.model.entity.UserDetails
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(private val api: LoginApiService, private val db: UserDao) {

    val retrofitStatus: MutableLiveData<RetrofitStatus> = MutableLiveData()

    suspend fun login(phone: Long, email: String): ResponseWrapper<LoginDetails?> {
        return handleApiCall(retrofitStatus) {
            api.login(phone, email)
        }
    }

    suspend fun sendOTP(phone: Long): ResponseWrapper<OtpStatus?> {
        return handleApiCall(retrofitStatus) {
            api.sendOtp(phone)
        }
    }

    suspend fun verifyOTP(otp: OTP): ResponseWrapper<UserDetails?> {
        return handleApiCall(retrofitStatus) {
            api.verifyOtp(otp)
        }
    }

    suspend fun insertUserDetails(userDetails: UserDetails) = db.insert(userDetails)
}