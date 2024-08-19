package com.example.cryptoviewerapp.model

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "cryptocurrency")
data class CryptoCurrency(
    @PrimaryKey val id: String,
    val name: String,
    val symbol: String,
    val image: String,
    @SerialName("current_price") val currentPrice: Double,
    @SerialName("ath_change_percentage") val changePercentage: Double
)

@Dao
interface CryptoCurrencyDao {

    @Query("SELECT * FROM cryptocurrency")
    suspend fun getAllCryptocurrencies(): List<CryptoCurrency>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cryptocurrencies: List<CryptoCurrency>)

    @Query("DELETE FROM cryptocurrency")
    suspend fun deleteAll()
}