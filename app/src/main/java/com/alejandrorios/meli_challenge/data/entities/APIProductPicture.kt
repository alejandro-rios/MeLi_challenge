package com.alejandrorios.meli_challenge.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class APIProductPicture(
    val id: String,
    @SerialName("secure_url")
    val url: String
)
