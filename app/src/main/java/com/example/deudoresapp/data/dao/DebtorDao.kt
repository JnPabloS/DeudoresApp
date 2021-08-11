package com.example.deudoresapp.data.dao

import androidx.room.*
import com.example.deudoresapp.data.entities.Debtor

@Dao
interface DebtorDao {
    @Insert
    fun createDebtor(debtor: Debtor)

    @Query("SELECT  * FROM table_debtor")
    fun getDebtors() : MutableList<Debtor>

    @Query("SELECT * FROM table_debtor where name LIKE :name")
    fun readDebtor(name: String): Debtor

    @Delete
    fun deleteDebtor(debtor: Debtor)

    @Update
    fun updateDebtor(debtor: Debtor)
}