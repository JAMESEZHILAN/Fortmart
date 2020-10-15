package com.fortmart.customer.ui.login.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fortmart.customer.data.network.ResponseWrapper
import com.fortmart.customer.data.repository.LoginRepository
import com.fortmart.customer.model.ErrorResponse
import com.fortmart.customer.model.request.OTP
import com.fortmart.customer.model.response.OtpStatus
import com.fortmart.customer.model.entity.UserDetails
import com.fortmart.customer.utils.EventWrapper
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val repo: LoginRepository) : ViewModel() {

    private val otpSuccess = MutableLiveData<EventWrapper<UserDetails?>>()
    private val otpFailure = MutableLiveData<EventWrapper<ErrorResponse?>>()
    private val resendOtpSuccess = MutableLiveData<EventWrapper<OtpStatus?>>()
    private val resendOtpFailure = MutableLiveData<EventWrapper<ErrorResponse?>>()

    suspend fun verifyOtp(otp: OTP) {
        when (val result = repo.verifyOTP(otp)) {
            is ResponseWrapper.Success -> otpSuccess.postValue(EventWrapper(result.dataResponse))
            is ResponseWrapper.Failure -> otpFailure.postValue(EventWrapper(result.errorResponse))
        }

    }

    suspend fun resendOtp(phone: Long) {
        when (val result = repo.sendOTP(phone)) {
            is ResponseWrapper.Success -> resendOtpSuccess.postValue(EventWrapper(result.dataResponse))
            is ResponseWrapper.Failure -> resendOtpFailure.postValue(EventWrapper(result.errorResponse))
        }
    }

    fun getOtpSuccess() = otpSuccess

    fun getOtpFailure() = otpFailure

    fun getResendOtpSuccess() = resendOtpSuccess

    fun getResendOtpFailure() = resendOtpFailure

    fun getRetrofitStatus() = repo.retrofitStatus

}