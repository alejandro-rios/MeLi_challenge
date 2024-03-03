package com.alejandrorios.meli_challenge.domain.models

data class SearchPaging(
    val total: Int,
    val primaryResults: Int,
    val offset: Int,
    val limit: Int
)
