package com.alejandrorios.meli_challenge.data.mappers

import com.alejandrorios.meli_challenge.data.entities.APIProduct
import com.alejandrorios.meli_challenge.data.entities.APIProductSeller
import com.alejandrorios.meli_challenge.data.entities.APISearchResults
import com.alejandrorios.meli_challenge.domain.models.Product
import com.alejandrorios.meli_challenge.domain.models.SearchResults

object APIProductMapper {

    fun mapAsProduct(apiModel: APIProduct): Product = with(apiModel) {
        Product(
            id,
            title,
            price,
            originalPrice,
            thumbnail,
            APIProductShippingMapper.mapAsProductShipping(shipping),
            APIProductSellerMapper.mapAsProductSeller(seller)
        )
    }
}
