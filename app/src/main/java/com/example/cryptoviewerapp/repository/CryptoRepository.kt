package com.example.cryptoviewerapp.repository

import com.example.cryptoviewerapp.model.CryptoCurrency
import com.example.cryptoviewerapp.model.CryptoCurrencyDetails
import com.example.cryptoviewerapp.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CryptoRepository @Inject constructor(private val apiService: ApiService) {

    private val ioDispatcher: CoroutineContext = Dispatchers.IO

    suspend fun getCryptoCurrencies(currency: String): List<CryptoCurrency> {
        return withContext(ioDispatcher) {
            apiService.getCryptoCurrencies(currency)
        }
    }

    suspend fun getCryptoCurrencyDetails(id: String): CryptoCurrencyDetails {
        return withContext(ioDispatcher) {
            apiService.getCryptoCurrencyDetails(id)
        }
    }
}