package com.base.app.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.base.app.model.entity.UserDetails

@Dao
interface UserDao: BaseDao<UserDetails>{

    @Query("SELECT * FROM UserDetails")
    fun getUserDetails(): UserDetails?

    @Query("SELECT token FROM UserDetails")
    fun getToken(): String?
}