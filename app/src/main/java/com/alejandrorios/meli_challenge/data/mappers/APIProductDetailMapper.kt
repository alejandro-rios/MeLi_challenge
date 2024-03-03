package com.alejandrorios.meli_challenge.data.mappers

import com.alejandrorios.meli_challenge.data.entities.APIProductDetail
import com.alejandrorios.meli_challenge.domain.models.ProductDetail

object APIProductDetailMapper {

    fun mapAsProductDetail(apiModel: APIProductDetail): ProductDetail = with(apiModel) {
        ProductDetail(
            id,
            siteId,
            title,
            price,
            basePrice,
            originalPrice,
            availableQuantity,
            thumbnail,
            permalink,
            warranty,
            pictures?.map { APIProductPicturesMapper.mapAsProductPicture(it) },
            APIProductShippingMapper.mapAsProductShipping(shipping)
        )
    }
}
