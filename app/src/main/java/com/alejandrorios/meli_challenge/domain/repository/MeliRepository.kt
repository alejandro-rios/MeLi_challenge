package com.alejandrorios.meli_challenge.domain.repository

import com.alejandrorios.meli_challenge.data.utils.CallResponse
import com.alejandrorios.meli_challenge.domain.models.ProductDetail
import com.alejandrorios.meli_challenge.domain.models.ProductDescription
import com.alejandrorios.meli_challenge.domain.models.SearchResults

interface MeliRepository {
    suspend fun searchProduct(
        searchQuery: String,
        limit: Int,
        offset: Int,
    ): CallResponse<SearchResults>

    suspend fun getProductDetails(productId: String): CallResponse<ProductDetail>

    suspend fun getProductDescription(productId: String): CallResponse<ProductDescription>
}
