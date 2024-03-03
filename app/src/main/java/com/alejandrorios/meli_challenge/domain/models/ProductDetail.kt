package com.alejandrorios.meli_challenge.domain.models

data class ProductDetail(
    val id: String,
    val siteId: String,
    val title: String,
    val price: Long,
    val basePrice: Long,
    val originalPrice: Long?,
    val availableQuantity: Long,
    val thumbnail: String,
    val permalink: String,
    val warranty: String,
    val pictures: List<ProductPicture>?,
    val shipping: ProductShipping
)
