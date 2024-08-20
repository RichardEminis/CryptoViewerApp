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
    @TypeConverters(DescriptionConverters::class)
    val description: Description,
    @TypeConverters(Converters::class)
    val categories: List<String>,
    @TypeConverters(ImageConverters::class)
    val image: Image,
)

@Serializable
data class Image(
    val large: String? = null
)

@Serializable
data class Description(
    val en: String
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

class DescriptionConverters {

    @TypeConverter
    fun fromDescription(value: String): Description {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun toDescription(description: Description): String {
        return Json.encodeToString(description)
    }
}

class ImageConverters {

    @TypeConverter
    fun fromImage(value: String): Image {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun toImage(image: Image): String {
        return Json.encodeToString(image)
    }
}

@Dao
interface CryptoDetailsDao {
    @Query("SELECT * FROM detail WHERE id = :cryptoId")
    fun getCryptoById(cryptoId: String): CryptoCurrencyDetails

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCryptoDetails(details: CryptoCurrencyDetails)
}