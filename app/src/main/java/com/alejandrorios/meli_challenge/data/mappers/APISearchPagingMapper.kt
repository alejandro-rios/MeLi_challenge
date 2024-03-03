package com.alejandrorios.meli_challenge.data.mappers

import com.alejandrorios.meli_challenge.data.entities.APISearchPaging
import com.alejandrorios.meli_challenge.domain.models.SearchPaging

object APISearchPagingMapper {

    fun mapAsSearchPaging(apiModel: APISearchPaging): SearchPaging = with(apiModel) {
        SearchPaging(
            total,
            primaryResults,
            offset,
            limit
        )
    }
}
