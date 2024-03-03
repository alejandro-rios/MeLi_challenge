package com.alejandrorios.meli_challenge.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class APISearchPaging(
    val total: Int,
    @SerialName("primary_results")
    val primaryResults: Int,
    val offset: Int,
    val limit: Int
)
