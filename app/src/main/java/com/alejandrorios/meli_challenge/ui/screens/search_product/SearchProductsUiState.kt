package com.alejandrorios.meli_challenge.ui.screens.search_product

import com.alejandrorios.meli_challenge.domain.models.Product

data class SearchProductsUiState(
    val searchQuery: String = "",
    val isActive: Boolean = false,
    val isLoading: Boolean = false,
    val isLoadingProducts: Boolean = false,
    val productsTotal: Int = 0,
    val products: List<Product> = emptyList(),
    val errorMessage: String? = null
) {
    val isEmpty = !isLoading && productsTotal == 0 && products.isEmpty() && errorMessage.isNullOrBlank()
}
