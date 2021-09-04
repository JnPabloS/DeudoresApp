package com.example.deudoresapp

import android.app.Application
import androidx.room.Room
import com.example.deudoresapp.data.local.DebtorDatabase
import com.example.deudoresapp.data.local.UserDatabase

class DeudoresApp : Application() {

    companion object{
        lateinit var database: DebtorDatabase

        lateinit var userDatabase: UserDatabase
    }
    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            this,
            DebtorDatabase::class.java,
            "debtor_db"
        ).allowMainThreadQueries()
            .build()

        userDatabase = Room.databaseBuilder(
            this,
            UserDatabase::class.java,
            "user_db"
        ).allowMainThreadQueries()
            .build()
    }
}