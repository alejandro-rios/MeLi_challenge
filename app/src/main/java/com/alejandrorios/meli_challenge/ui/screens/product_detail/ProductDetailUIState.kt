package com.alejandrorios.meli_challenge.ui.screens.product_detail

import com.alejandrorios.meli_challenge.domain.models.ProductDetail

data class ProductDetailUIState(
    val isLoading: Boolean = true,
    val productDetails: ProductDetail? = null,
    val productDescription: String = "",
    val errorMessage: String? = null
)
