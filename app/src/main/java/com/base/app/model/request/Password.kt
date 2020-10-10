package com.base.app.model.request

data class Password(
    val phone: Long,
    val email: String,
    val password: String
)