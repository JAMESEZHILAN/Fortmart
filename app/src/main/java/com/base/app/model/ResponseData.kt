package com.base.app.model

data class ResponseData<T> (
    val count: Int,
    val data: List<T>
)