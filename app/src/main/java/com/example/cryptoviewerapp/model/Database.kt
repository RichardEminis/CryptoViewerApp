package com.example.cryptoviewerapp.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CryptoCurrency::class], version = 1, exportSchema = false)
abstract class CryptoCurrencyDatabase : RoomDatabase() {
    abstract fun cryptoCurrencyDao(): CryptoCurrencyDao
}