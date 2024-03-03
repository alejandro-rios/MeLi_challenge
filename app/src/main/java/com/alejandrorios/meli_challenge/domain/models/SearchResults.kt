package com.alejandrorios.meli_challenge.domain.models

data class SearchResults (
    val siteId: String,
    val query: String,
    val paging: SearchPaging,
    val results: List<Product>
)
