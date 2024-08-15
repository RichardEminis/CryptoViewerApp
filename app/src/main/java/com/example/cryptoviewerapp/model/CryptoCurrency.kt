package com.example.cryptoviewerapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CryptoCurrency(
    val id: String,
    val name: String,
    val symbol: String,
    val image: String,
    @SerialName("current_price") val currentPrice: Double
)