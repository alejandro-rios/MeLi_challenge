package com.alejandrorios.meli_challenge.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class APIProductDetail(
    val id: String,
    @SerialName("site_id")
    val siteId: String,
    val title: String,
    val price: Long,
    @SerialName("base_price")
    val basePrice: Long,
    @SerialName("original_price")
    val originalPrice: Long?,
    @SerialName("initial_quantity")
    val availableQuantity: Long,
    val thumbnail: String,
    val permalink: String,
    val warranty: String,
    val pictures: List<APIProductPicture>?,
    val shipping: APIProductShipping
)
