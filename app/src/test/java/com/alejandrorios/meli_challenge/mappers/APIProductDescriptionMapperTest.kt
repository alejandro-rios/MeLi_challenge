package com.alejandrorios.meli_challenge.mappers

import com.alejandrorios.meli_challenge.data.entities.APIProductDescription
import com.alejandrorios.meli_challenge.data.mappers.APIProductDescriptionMapper
import com.alejandrorios.meli_challenge.domain.models.ProductDescription
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class APIProductDescriptionMapperTest {
    @Test
    fun `When mapAsProductDescription is called then should return ProductDescription`() {
        val apiModel = APIProductDescription("product description")

        val result = APIProductDescriptionMapper.mapAsProductDescription(apiModel)

        result shouldBeEqualTo ProductDescription("product description")
    }
}
