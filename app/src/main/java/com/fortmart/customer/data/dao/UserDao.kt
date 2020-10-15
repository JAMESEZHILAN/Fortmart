package com.fortmart.customer.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.fortmart.customer.model.entity.UserDetails

@Dao
interface UserDao: BaseDao<UserDetails>{

    @Query("SELECT * FROM UserDetails")
    fun getUserDetails(): UserDetails?

    @Query("SELECT token FROM UserDetails")
    fun getToken(): String?
}