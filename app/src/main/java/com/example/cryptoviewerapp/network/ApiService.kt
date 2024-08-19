package com.example.cryptoviewerapp.network

import com.example.cryptoviewerapp.model.CryptoCurrency
import com.example.cryptoviewerapp.model.CryptoCurrencyDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("coins/markets")
    suspend fun getCryptoCurrencies(
        @Query("vs_currency") currency: String,
        @Query("per_page") perPage: Int = 20,
    ): List<CryptoCurrency>

    @GET("coins/{id}")
    suspend fun getCryptoCurrencyDetails(@Path("id") id: String): CryptoCurrencyDetails
}