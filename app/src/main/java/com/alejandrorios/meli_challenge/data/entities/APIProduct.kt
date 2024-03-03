package com.alejandrorios.meli_challenge.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class APIProduct(
    val id: String,
    val title: String,
    val price: Long,
    @SerialName("original_price")
    val originalPrice: Long?,
    val thumbnail: String,
    val shipping: APIProductShipping,
    val seller: APIProductSeller,
)
