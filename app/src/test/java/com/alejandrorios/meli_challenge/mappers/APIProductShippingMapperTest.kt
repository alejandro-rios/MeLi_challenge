package com.alejandrorios.meli_challenge.mappers

import com.alejandrorios.meli_challenge.data.entities.APIProductShipping
import com.alejandrorios.meli_challenge.data.mappers.APIProductShippingMapper
import com.alejandrorios.meli_challenge.domain.models.ProductShipping
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class APIProductShippingMapperTest {
    @Test
    fun `When mapAsProductShipping is called then should return ProductShipping`() {
        val apiModel = APIProductShipping(freeShipping = true, storePickUp = false)

        val result = APIProductShippingMapper.mapAsProductShipping(apiModel)

        result shouldBeEqualTo ProductShipping(freeShipping = true, storePickUp = false)
    }
}
