package com.example.deudoresapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.deudoresapp.data.entities.User

@Dao
interface UserDao {
    @Insert
    fun createUser(user: User)

    @Query("SELECT * FROM table_user where email LIKE :email")
    fun readUser(email: String): User
}