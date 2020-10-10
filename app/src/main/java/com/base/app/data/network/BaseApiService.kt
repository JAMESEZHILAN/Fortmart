package com.base.app.data.network

import retrofit2.http.*

interface BaseApiService {

    @POST("")
    suspend fun logout()

}
