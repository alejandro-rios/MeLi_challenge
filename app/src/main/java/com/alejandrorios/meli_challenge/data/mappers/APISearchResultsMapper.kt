package com.alejandrorios.meli_challenge.data.mappers

import com.alejandrorios.meli_challenge.data.entities.APISearchResults
import com.alejandrorios.meli_challenge.domain.models.SearchResults

object APISearchResultsMapper {

    fun mapAsSearchResults(apiModel: APISearchResults): SearchResults = with(apiModel) {
        SearchResults(
            siteId,
            query,
            APISearchPagingMapper.mapAsSearchPaging(paging),
            results.map { APIProductMapper.mapAsProduct(it) },
        )
    }
}
