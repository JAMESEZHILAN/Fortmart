package com.base.app.model.response

import java.io.Serializable


data class LoginDetails(
    val id: Int?,
    val countryCode: String?,
    val phone: Long?,
    val email: String?,
    val password: String?
): Serializable