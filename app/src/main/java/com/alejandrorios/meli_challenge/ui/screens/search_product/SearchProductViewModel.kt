package com.alejandrorios.meli_challenge.ui.screens.search_product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandrorios.meli_challenge.data.utils.CallResponse.Failure
import com.alejandrorios.meli_challenge.data.utils.CallResponse.Success
import com.alejandrorios.meli_challenge.domain.repository.MeliRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val PAGE_SIZE = 10
class SearchProductViewModel(private val meliRepository: MeliRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchProductsUiState())
    val uiState: StateFlow<SearchProductsUiState> = _uiState.asStateFlow()

    fun onSearchTextChange(query: String) {
        _uiState.update { currentState ->
            currentState.copy(searchQuery = query, isActive = true)
        }
    }

    fun searchProduct(query: String) {
        _uiState.update { currentState ->
            currentState.copy(isLoading = true, isActive = false)
        }

        viewModelScope.launch {
            when (val result = meliRepository.searchProduct(query, PAGE_SIZE, 0)) {
                is Failure -> _uiState.update { currentState ->
                    currentState.copy(isLoading = false, errorMessage = result.t.message)
                }
                is Success -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            searchQuery = query,
                            isLoading = false,
                            productsTotal = result.data.paging.total,
                            products = result.data.results,
                            errorMessage = null
                        )
                    }
                }
            }
        }
    }

    fun onToggleSearch(active: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(isActive = active)
        }
    }

    fun onSearchTextClear() {
        _uiState.update { currentState ->
            currentState.copy(searchQuery = "")
        }
    }

    fun loadMoreResults() {
        val products = _uiState.value.products.toMutableList()
        val thresholdTotal = _uiState.value.products.size

        if (_uiState.value.productsTotal == thresholdTotal) return

        _uiState.update { currentState ->
            currentState.copy(isLoadingProducts = true)
        }
        val offset = _uiState.value.products.size

        viewModelScope.launch {
            when (val result = meliRepository.searchProduct(_uiState.value.searchQuery, PAGE_SIZE, offset)) {
                is Failure -> _uiState.update { currentState ->
                    currentState.copy(isLoadingProducts = false, errorMessage = result.t.message)
                }

                is Success -> {
                    products.addAll(result.data.results)
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoadingProducts = false,
                            productsTotal = result.data.paging.total,
                            products = products,
                        )
                    }
                }
            }
        }
    }
}
