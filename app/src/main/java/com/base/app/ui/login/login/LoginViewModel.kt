package com.base.app.ui.login.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.base.app.data.network.ResponseWrapper
import com.base.app.data.repository.LoginRepository
import com.base.app.model.ErrorResponse
import com.base.app.model.response.LoginDetails
import com.base.app.model.response.OtpStatus
import com.base.app.utils.EventWrapper
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val repo: LoginRepository) : ViewModel() {

    private val usernameSuccess = MutableLiveData<EventWrapper<LoginDetails?>>()
    private val usernameFailure = MutableLiveData<EventWrapper<ErrorResponse?>>()
    private val otpSendSuccess = MutableLiveData<EventWrapper<OtpStatus?>>()
    private val otpSendFailure = MutableLiveData<EventWrapper<ErrorResponse?>>()

//    suspend fun loginWith(phone: Long = 0, email: String = "") {
//        when (val result = repo.login(phone, email)) {
//            is ResponseWrapper.Success -> usernameSuccess.postValue(EventWrapper(result.dataResponse))
//            is ResponseWrapper.Failure -> usernameFailure.postValue(EventWrapper(result.errorResponse))
//        }
//    }

    suspend fun sendOtp(phone: Long) {
        when (val result = repo.sendOTP(phone)) {
            is ResponseWrapper.Success -> otpSendSuccess.postValue(EventWrapper(result.dataResponse))
            is ResponseWrapper.Failure -> otpSendFailure.postValue(EventWrapper(result.errorResponse))
        }
    }

    fun getLoginSuccess() = usernameSuccess

    fun getLoginFailure() = usernameFailure

//    fun getOtpSuccess() = otpSendSuccess
//
//    fun getOtpFailure() = otpSendFailure

    fun getRetrofitStatus() = repo.retrofitStatus
}