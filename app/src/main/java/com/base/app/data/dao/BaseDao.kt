package com.base.app.data.dao

import androidx.room.Insert

interface BaseDao<T>{
    @Insert
    suspend fun insert(vararg entry: T)
}