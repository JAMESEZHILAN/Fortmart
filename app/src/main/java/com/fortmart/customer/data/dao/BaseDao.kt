package com.fortmart.customer.data.dao

import androidx.room.Insert

interface BaseDao<T>{
    @Insert
    suspend fun insert(vararg entry: T)
}