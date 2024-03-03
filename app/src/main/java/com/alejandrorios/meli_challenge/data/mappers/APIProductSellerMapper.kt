package com.alejandrorios.meli_challenge.data.mappers

import com.alejandrorios.meli_challenge.data.entities.APIProductSeller
import com.alejandrorios.meli_challenge.domain.models.ProductSeller

object APIProductSellerMapper {

    fun mapAsProductSeller(apiModel: APIProductSeller): ProductSeller = with(apiModel) {
        ProductSeller(
            id,
            nickname
        )
    }
}
