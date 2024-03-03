package com.alejandrorios.meli_challenge.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class APIProductDescription(
    @SerialName("plain_text")
    val description: String,
)
