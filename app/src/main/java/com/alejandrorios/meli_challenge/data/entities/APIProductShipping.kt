package com.alejandrorios.meli_challenge.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class APIProductShipping(
    @SerialName("free_shipping")
    val freeShipping: Boolean,
    @SerialName("store_pick_up")
    val storePickUp: Boolean
)
