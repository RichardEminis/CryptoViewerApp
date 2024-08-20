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
    val cryptocurrency: List<CryptoCurrency>? = emptyList(),
    var error: String? = null,
    val isLoading: Boolean = false,
    val isInternetConnected: Boolean = false
)

@HiltViewModel
class CryptoViewModel @Inject constructor(private val repository: CryptoRepository) : ViewModel() {

    private val _cryptoUiState = MutableLiveData(CryptoUiState(isLoading = true))
    val cryptoCurrencies: LiveData<CryptoUiState>
        get() = _cryptoUiState

    var currentCurrency: String = "usd"

    init {
        getCryptoCurrencies(currentCurrency)
    }

    fun getCryptoCurrencies(currency: String) {
        _cryptoUiState.value = cryptoCurrencies.value?.copy(isLoading = true)

        viewModelScope.launch {
            if (repository.getCurrenciesFromCache().isEmpty()){
                val response = repository.getCryptoCurrencies(currency)
                _cryptoUiState.value =
                    cryptoCurrencies.value?.copy(cryptocurrency = response, error = "Интернет отсутствует", isLoading = false)
            } else {
                try {
                    val cachedCategories = repository.getCurrenciesFromCache()
                    _cryptoUiState.value =
                        cryptoCurrencies.value?.copy(cryptocurrency = cachedCategories, error = null, isLoading = false)

                    repository.saveCurrenciesToCache(cachedCategories)
                } catch (e:Exception){
                    _cryptoUiState.value =
                        cryptoCurrencies.value?.copy(error = "Не удалось загрузить данные", isLoading = true)
                }
            }
        }
    }

    fun refreshCryptoData(currency: String) {
        viewModelScope.launch {
                val result = repository.getCryptoCurrencies(currency)
            _cryptoUiState.value = CryptoUiState(cryptocurrency = result)
        }
    }
}