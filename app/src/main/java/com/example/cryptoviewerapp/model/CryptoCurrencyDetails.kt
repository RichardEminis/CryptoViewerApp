package com.example.cryptoviewerapp.model

import kotlinx.serialization.Serializable

@Serializable
data class CryptoCurrencyDetails(
    val id: String,
    val name: String,
    val description: String,
    val categories: List<String>,
    val image: String
)