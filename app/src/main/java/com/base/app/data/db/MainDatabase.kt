package com.base.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.base.app.data.dao.UserDao
import com.base.app.model.entity.UserDetails

@Database(entities = [UserDetails::class], version = 1, exportSchema = false)
abstract class MainDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}