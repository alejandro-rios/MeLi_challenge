package com.alejandrorios.meli_challenge.mappers

import com.alejandrorios.meli_challenge.data.entities.APIProductSeller
import com.alejandrorios.meli_challenge.data.mappers.APIProductSellerMapper
import com.alejandrorios.meli_challenge.domain.models.ProductSeller
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class APIProductSellerMapperTest {
    @Test
    fun `When mapAsProductSeller is called then should return ProductSeller`() {
        val apiModel = APIProductSeller(123456, "product seller nickname")

        val result = APIProductSellerMapper.mapAsProductSeller(apiModel)

        result shouldBeEqualTo ProductSeller(123456, "product seller nickname")
    }
}
