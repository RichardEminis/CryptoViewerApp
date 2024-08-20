package com.example.cryptoviewerapp.ui.cryptoDetails

import ERROR_TAG
import android.util.Log
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
    val detailsCryptocurrency: CryptoCurrencyDetails? = null,
    val isLoading: Boolean = false,
)

@HiltViewModel
class CryptoDetailsViewModel @Inject constructor(private val repository: CryptoRepository) :
    ViewModel() {

    private val _cryptoDetailsUiState = MutableLiveData(CryptoDetailsUiState(isLoading = true))
    val cryptoDetailsUiState: LiveData<CryptoDetailsUiState>
        get() = _cryptoDetailsUiState

    fun getCryptoCurrenciesDetails(cryptoId: String) {
        _cryptoDetailsUiState.value = cryptoDetailsUiState.value?.copy(isLoading = true)

        viewModelScope.launch {
            if (repository.getCryptoByIdFromCache(cryptoId) != null) {
                val cachedDetails = repository.getCryptoByIdFromCache(cryptoId)
                _cryptoDetailsUiState.value = cryptoDetailsUiState.value?.copy(
                    detailsCryptocurrency = cachedDetails,
                    isLoading = false,
                )
            } else {
                try {
                    val response = repository.getCryptoCurrencyDetails(cryptoId)
                    _cryptoDetailsUiState.value = cryptoDetailsUiState.value?.copy(
                        detailsCryptocurrency = response,
                        isLoading = false
                    )
                    repository.saveDetailsToCache(response)
                } catch (e: Exception) {
                    Log.e(ERROR_TAG, e.toString())
                    _cryptoDetailsUiState.value = cryptoDetailsUiState.value?.copy(
                        isLoading = true
                    )
                }
            }
        }
    }
}