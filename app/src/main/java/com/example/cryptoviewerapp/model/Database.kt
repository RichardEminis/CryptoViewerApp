package com.example.cryptoviewerapp.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CryptoCurrency::class, CryptoCurrencyDetails::class], version = 2)
@TypeConverters(Converters::class)
abstract class CryptoCurrencyDatabase : RoomDatabase() {
    abstract fun cryptoCurrencyDao(): CryptoCurrencyDao
    abstract fun cryptoCurrencyDetailDao(): CryptoDetailsDao
}