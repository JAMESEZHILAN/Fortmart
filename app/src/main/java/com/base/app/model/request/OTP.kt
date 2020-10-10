package com.base.app.model.request

data class OTP(
    val phone: Long,
    val email: String,
    val otp: Int
)