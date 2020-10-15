package com.fortmart.customer.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fortmart.customer.data.dao.UserDao
import com.fortmart.customer.model.entity.UserDetails

@Database(entities = [UserDetails::class], version = 1, exportSchema = false)
abstract class MainDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}