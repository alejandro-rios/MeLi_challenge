package com.alejandrorios.meli_challenge.mappers

import com.alejandrorios.meli_challenge.data.entities.APIProductPicture
import com.alejandrorios.meli_challenge.data.mappers.APIProductPicturesMapper
import com.alejandrorios.meli_challenge.domain.models.ProductPicture
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class APIProductPicturesMapperTest {
    @Test
    fun `When mapAsProductPicture is called then should return ProductPicture`() {
        val apiModel = APIProductPicture("pictureId", "pictureUrl")

        val result = APIProductPicturesMapper.mapAsProductPicture(apiModel)

        result shouldBeEqualTo ProductPicture("pictureId", "pictureUrl")
    }
}
