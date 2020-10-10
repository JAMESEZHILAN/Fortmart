package com.base.app.ui.login.otp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.base.app.data.network.ResponseWrapper
import com.base.app.data.repository.LoginRepository
import com.base.app.model.ErrorResponse
import com.base.app.model.request.OTP
import com.base.app.model.response.OtpStatus
import com.base.app.model.entity.UserDetails
import com.base.app.utils.EventWrapper
import javax.inject.Inject

class OtpViewModel @Inject constructor(private val repo: LoginRepository) : ViewModel() {

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