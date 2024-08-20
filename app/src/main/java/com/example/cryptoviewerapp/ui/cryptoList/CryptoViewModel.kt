package com.example.cryptoviewerapp.ui.cryptoList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoviewerapp.model.CryptoCurrency
import com.example.cryptoviewerapp.repository.CryptoRepository
import com.example.cryptoviewerapp.ulils.ERROR_TAG
import com.example.cryptoviewerapp.ulils.USD_CURRENCY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CryptoUiState(
    val cryptocurrency: List<CryptoCurrency>? = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class CryptoViewModel @Inject constructor(private val repository: CryptoRepository) : ViewModel() {

    private val _cryptoUiState = MutableLiveData(CryptoUiState(isLoading = true))
    val cryptoCurrencies: LiveData<CryptoUiState>
        get() = _cryptoUiState

    var currentCurrency: String = USD_CURRENCY

    init {
        getCryptoCurrencies(currentCurrency)
    }

    fun getCryptoCurrencies(currency: String) {
        _cryptoUiState.value = cryptoCurrencies.value?.copy(isLoading = true)

        viewModelScope.launch {
            if (repository.getCurrenciesFromCache().isEmpty()) {
                val response = repository.getCryptoCurrencies(currency)
                _cryptoUiState.value =
                    cryptoCurrencies.value?.copy(cryptocurrency = response, isLoading = false)
            } else {
                try {
                    val cachedCategories = repository.getCurrenciesFromCache()
                    _cryptoUiState.value =
                        cryptoCurrencies.value?.copy(
                            cryptocurrency = cachedCategories,
                            isLoading = false
                        )

                    repository.saveCurrenciesToCache(cachedCategories)
                } catch (e: Exception) {
                    Log.e(ERROR_TAG, e.toString())
                    _cryptoUiState.value =
                        cryptoCurrencies.value?.copy(isLoading = true)
                }
            }
        }
    }

    fun updateCache(currency: String) {
        _cryptoUiState.value = cryptoCurrencies.value?.copy(isLoading = true)

        viewModelScope.launch {
            val response = repository.getCryptoCurrencies(currency)
            _cryptoUiState.value =
                cryptoCurrencies.value?.copy(cryptocurrency = response, isLoading = false)
        }
    }
}