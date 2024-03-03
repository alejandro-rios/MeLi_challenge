package com.alejandrorios.meli_challenge.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class APIProductSeller(
    val id: Long,
    val nickname: String,
)
