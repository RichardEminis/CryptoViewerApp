package com.example.cryptoviewerapp.model

data class CryptoCurrencyDetails(
    val id: String,
    val name: String,
    val description: String,
    val categories: List<String>,
    val image: String
)