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
    var error: String? = null
)

@HiltViewModel
class CryptoViewModel @Inject constructor(private val repository: CryptoRepository) : ViewModel() {

    private val _cryptoUiState = MutableLiveData(CryptoUiState())
    val cryptoCurrencies: LiveData<CryptoUiState>
        get() = _cryptoUiState

    private var _error = MutableLiveData<String?>()
    var error: LiveData<String?> = _error

    var currentCurrency: String = "usd"

    init {
        getCryptoCurrencies(currentCurrency)
    }

    fun getCryptoCurrencies(currency: String) {

        viewModelScope.launch {
            try {
                val response = repository.getCryptoCurrencies(currency)
                _cryptoUiState.value =
                    cryptoCurrencies.value?.copy(cryptocurrency = response, error = null)
                _error.value = null
            } catch (e: Exception) {
                _cryptoUiState.value = null
                _error.value = "Произошла ошибка при загрузке"
            }
        }
    }
}