package com.example.deudoresapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.deudoresapp.data.dao.UserDao
import com.example.deudoresapp.data.entities.User

@Database(entities = [User::class], version = 1)
abstract class UserDatabase: RoomDatabase() {

    abstract fun UserDao(): UserDao
}