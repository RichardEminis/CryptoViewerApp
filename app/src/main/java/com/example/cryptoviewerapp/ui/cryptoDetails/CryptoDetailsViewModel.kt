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
    val detailsCryptocurrency: CryptoCurrencyDetails? = null,
    var error: String? = null,
    val isLoading: Boolean = false,
    val isInternetConnected: Boolean = false
)

@HiltViewModel
class CryptoDetailsViewModel @Inject constructor(private val repository: CryptoRepository) :
    ViewModel() {

    private val _cryptoDetailsUiState = MutableLiveData(CryptoDetailsUiState())
    val cryptoDetailsUiState: LiveData<CryptoDetailsUiState>
        get() = _cryptoDetailsUiState

    fun getCryptoCurrenciesDetails(cryptoId: String) {
        _cryptoDetailsUiState.value = cryptoDetailsUiState.value?.copy(isLoading = true)

        viewModelScope.launch {
            if (repository.getCryptoByIdFromCache(cryptoId) != null) {
                val cachedDetails = repository.getCryptoByIdFromCache(cryptoId)
                _cryptoDetailsUiState.value = cryptoDetailsUiState.value?.copy(
                    detailsCryptocurrency = cachedDetails,
                    isLoading = true,
                    error = "Произошла ошибка при загрузке данных"
                )
            } else {
                try {
                    val response = repository.getCryptoCurrencyDetails(cryptoId)
                    _cryptoDetailsUiState.value = cryptoDetailsUiState.value?.copy(
                        detailsCryptocurrency = response,
                        isLoading = true
                    )
                    repository.saveDetailsToCache(response)
                } catch (e: Exception) {
                    _cryptoDetailsUiState.value = cryptoDetailsUiState.value?.copy(
                        error = "Fatal error",
                        isLoading = false
                    )
                }
            }
        }
    }
}