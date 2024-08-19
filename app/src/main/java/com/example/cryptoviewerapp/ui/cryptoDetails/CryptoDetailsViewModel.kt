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
    val detailsCryptocurrency: CryptoCurrencyDetails? = null
)

@HiltViewModel
class CryptoDetailsViewModel @Inject constructor(private val repository: CryptoRepository) : ViewModel() {

    private val _cryptoDetailsUiState = MutableLiveData(CryptoDetailsUiState())
    val cryptoDetailsUiState: LiveData<CryptoDetailsUiState>
        get() = _cryptoDetailsUiState

    fun getCryptoCurrenciesDetails(cryptoId: String) {
        viewModelScope.launch {
            try {
                val cachedCategories = repository.getCryptoByIdFromCache(cryptoId)
                _cryptoDetailsUiState.value =
                    cryptoDetailsUiState.value?.copy(detailsCryptocurrency = cachedCategories)

                val response = repository.getCryptoCurrencyDetails(cryptoId)
                _cryptoDetailsUiState.value = cryptoDetailsUiState.value?.copy(detailsCryptocurrency = response)

                repository.saveDetailsToCache(response)
            } catch (e: Exception) {
                _cryptoDetailsUiState.value = null
            }
        }
    }
}