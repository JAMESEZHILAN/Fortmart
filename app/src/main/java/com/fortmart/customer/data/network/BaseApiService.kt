package com.fortmart.customer.data.network

import retrofit2.http.*

interface BaseApiService {

    @POST("")
    suspend fun logout()

}
