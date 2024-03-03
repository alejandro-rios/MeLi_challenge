package com.alejandrorios.meli_challenge.data.mappers

import com.alejandrorios.meli_challenge.data.entities.APIProductShipping
import com.alejandrorios.meli_challenge.domain.models.ProductShipping

object APIProductShippingMapper {

    fun mapAsProductShipping(apiModel: APIProductShipping): ProductShipping = with(apiModel) {
        ProductShipping(
            freeShipping,
            storePickUp
        )
    }
}
