package com.alejandrorios.meli_challenge.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class APISearchResults (
    @SerialName("site_id")
    val siteId: String,
    val query: String,
    val paging: APISearchPaging,
    val results: List<APIProduct>
)
