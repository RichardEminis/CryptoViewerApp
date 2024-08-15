package com.example.cryptoviewerapp.ui.cryptoDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoviewerapp.model.CryptoCurrencyDetails
import com.example.cryptoviewerapp.repository.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CryptoDetailsUiState(
    val hotel: CryptoCurrencyDetails? = null
)

@HiltViewModel
class CryptoViewModel @Inject constructor(private val repository: CryptoRepository) : ViewModel() {

    private val _cryptoDetailsUiState = MutableLiveData(CryptoDetailsUiState())
    val cryptoDetailsUiState: LiveData<CryptoDetailsUiState>
        get() = _cryptoDetailsUiState

    fun getCryptoCurrenciesDetails(currency: String) {
        viewModelScope.launch {
            try {
                val response = repository.getCryptoCurrencyDetails(currency)
                _cryptoDetailsUiState.value = cryptoDetailsUiState.value?.copy()
            } catch (e: Exception) {
                _cryptoDetailsUiState.value = null
            }
        }
    }
}