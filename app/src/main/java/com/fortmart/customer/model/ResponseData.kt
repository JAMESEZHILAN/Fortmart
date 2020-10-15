package com.fortmart.customer.model

data class ResponseData<T> (
    val count: Int,
    val data: List<T>
)