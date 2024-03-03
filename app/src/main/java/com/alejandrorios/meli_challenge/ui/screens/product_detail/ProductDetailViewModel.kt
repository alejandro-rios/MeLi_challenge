package com.alejandrorios.meli_challenge.ui.screens.product_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandrorios.meli_challenge.data.utils.CallResponse.Failure
import com.alejandrorios.meli_challenge.data.utils.CallResponse.Success
import com.alejandrorios.meli_challenge.domain.repository.MeliRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductDetailViewModel(private val meliRepository: MeliRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailUIState())
    val uiState: StateFlow<ProductDetailUIState> = _uiState.asStateFlow()

    fun getProductDetail(productId: String) {
        _uiState.update { currentState ->
            currentState.copy(isLoading = true)
        }
        viewModelScope.launch {
            delay(500)
            when (val productDetailResult = meliRepository.getProductDetails(productId)) {
                is Failure -> _uiState.update { currentState ->
                    currentState.copy(isLoading = false, errorMessage = productDetailResult.t.message)
                }

                is Success -> {
                    when (val productDescriptionResult = meliRepository.getProductDescription(productId)) {
                        is Failure -> _uiState.update { currentState ->
                            currentState.copy(isLoading = false, errorMessage = productDescriptionResult.t.message)
                        }

                        is Success -> {
                            _uiState.update { currentState ->
                                currentState.copy(
                                    isLoading = false,
                                    productDetails = productDetailResult.data,
                                    productDescription = productDescriptionResult.data.description,
                                    errorMessage = null
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
