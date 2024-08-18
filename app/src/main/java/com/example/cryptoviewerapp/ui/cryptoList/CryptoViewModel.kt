package com.example.cryptoviewerapp.ui.cryptoList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoviewerapp.model.CryptoCurrency
import com.example.cryptoviewerapp.repository.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CryptoUiState(
    val cryptocurrency: List<CryptoCurrency>? = null,
)

@HiltViewModel
class CryptoViewModel @Inject constructor(private val repository: CryptoRepository) : ViewModel() {

    private val _cryptoUiState = MutableLiveData(CryptoUiState())
    val cryptoCurrencies: LiveData<CryptoUiState>
        get() = _cryptoUiState

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    var currentCurrency: String = "usd"

    fun getCryptoCurrencies(currency: String) {
        _isLoading.value = true

        currentCurrency = currency

        viewModelScope.launch {
            try {
                val response = repository.getCryptoCurrencies(currency)
                _cryptoUiState.value = cryptoCurrencies.value?.copy(cryptocurrency = response)
            } catch (e: Exception) {
                _cryptoUiState.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }
}