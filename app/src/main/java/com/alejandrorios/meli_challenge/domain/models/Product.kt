package com.alejandrorios.meli_challenge.domain.models

data class Product(
    val id: String,
    val title: String,
    val price: Long,
    val originalPrice: Long?,
    val thumbnail: String,
    val shipping: ProductShipping,
    val seller: ProductSeller
)
