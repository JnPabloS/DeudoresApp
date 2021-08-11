package com.example.deudoresapp

import android.app.Application
import androidx.room.Room
import com.example.deudoresapp.data.DebtorDatabase

class DeudoresApp : Application() {

    companion object{
        lateinit var database: DebtorDatabase
    }
    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            this,
            DebtorDatabase::class.java,
            "debtor_db"
        ).allowMainThreadQueries()
            .build()
    }
}