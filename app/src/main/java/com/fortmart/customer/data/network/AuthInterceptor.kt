package com.fortmart.customer.data.network

import com.fortmart.customer.data.dao.UserDao
import com.fortmart.customer.shared.Constants.AUTHORIZATION
import com.fortmart.customer.shared.Constants.TOKEN_PREFIX
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Singleton

@Singleton
class AuthInterceptor (private val db: UserDao) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val authenticatedRequest = chain
            .request()
            .newBuilder()
        val token = db.getToken()
        token?.let {
            if (it.isNotEmpty())
                authenticatedRequest.header(AUTHORIZATION, TOKEN_PREFIX+it)
        }
        return chain.proceed(authenticatedRequest.build())
    }
}