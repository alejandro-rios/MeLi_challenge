package com.alejandrorios.meli_challenge.data.mappers

import com.alejandrorios.meli_challenge.data.entities.APIProductDescription
import com.alejandrorios.meli_challenge.domain.models.ProductDescription

object APIProductDescriptionMapper {

    fun mapAsProductDescription(apiModel: APIProductDescription): ProductDescription = with(apiModel) {
        ProductDescription(
            description
        )
    }
}
