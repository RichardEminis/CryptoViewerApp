package com.example.cryptoviewerapp.model

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
@Entity(tableName = "detail")
data class CryptoCurrencyDetails(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    @TypeConverters(Converters::class)
    val categories: List<String>,
    val image: String
)

class Converters {

    @TypeConverter
    fun fromStringList(value: String): List<String> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun toStringList(list: List<String>): String {
        return Json.encodeToString(list)
    }
}

@Dao
interface CryptoDetailsDao {
    @Query("SELECT * FROM detail WHERE id = :cryptoId")
    fun getCryptoById(cryptoId: String): CryptoCurrencyDetails

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCryptoDetails(details: CryptoCurrencyDetails)
}