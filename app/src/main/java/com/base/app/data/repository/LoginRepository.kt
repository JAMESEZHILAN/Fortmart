package com.base.app.data.repository

import androidx.lifecycle.MutableLiveData
import com.base.app.data.dao.UserDao
import com.base.app.data.network.LoginApiService
import com.base.app.data.network.ResponseWrapper
import com.base.app.data.network.handleApiCall
import com.base.app.enums.RetrofitStatus
import com.base.app.model.request.NewPassword
import com.base.app.model.request.OTP
import com.base.app.model.request.Password
import com.base.app.model.response.LoginDetails
import com.base.app.model.response.OtpStatus
import com.base.app.model.entity.UserDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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