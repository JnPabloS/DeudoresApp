package com.example.deudoresapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "table_debtor")
data class Debtor (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "amount") val amount: Long
) : Serializable
