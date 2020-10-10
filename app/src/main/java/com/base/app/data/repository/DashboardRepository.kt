package com.base.app.data.repository

import androidx.lifecycle.MutableLiveData
import com.base.app.data.dao.UserDao
import com.base.app.data.network.BaseApiService
import com.base.app.data.network.ResponseWrapper
import com.base.app.data.network.handleApiCall
import com.base.app.enums.RetrofitStatus
import com.base.app.model.entity.UserDetails
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