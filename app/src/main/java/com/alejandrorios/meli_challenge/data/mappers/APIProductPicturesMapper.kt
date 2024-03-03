package com.alejandrorios.meli_challenge.data.mappers

import com.alejandrorios.meli_challenge.data.entities.APIProductPicture
import com.alejandrorios.meli_challenge.domain.models.ProductPicture

object APIProductPicturesMapper {

    fun mapAsProductPicture(apiModel: APIProductPicture): ProductPicture = with(apiModel) {
        ProductPicture(
            id,
            url
        )
    }
}
