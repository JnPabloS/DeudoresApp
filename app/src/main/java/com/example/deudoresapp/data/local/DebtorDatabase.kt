package com.example.deudoresapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.deudoresapp.data.local.dao.DebtorDao
import com.example.deudoresapp.data.local.entities.Debtor

@Database(entities = [Debtor::class], version = 1)
abstract class DebtorDatabase: RoomDatabase() {

    abstract fun DebtorDao(): DebtorDao
}