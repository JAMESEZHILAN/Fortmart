package com.fortmart.customer.data.repository

import androidx.lifecycle.MutableLiveData
import com.fortmart.customer.data.dao.UserDao
import com.fortmart.customer.data.network.BaseApiService
import com.fortmart.customer.data.network.ResponseWrapper
import com.fortmart.customer.data.network.handleApiCall
import com.fortmart.customer.enums.RetrofitStatus
import com.fortmart.customer.model.entity.UserDetails
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DashboardRepository @Inject constructor(
        private val api: BaseApiService,
        private val db: UserDao
) {

    val retrofitStatus = MutableLiveData(RetrofitStatus.NO_SYNC)

    suspend fun logout(): ResponseWrapper<Unit?> {
        return handleApiCall(retrofitStatus) {
            api.logout()
        }
    }

    suspend fun getUserDetails(): UserDetails? = db.getUserDetails()

}