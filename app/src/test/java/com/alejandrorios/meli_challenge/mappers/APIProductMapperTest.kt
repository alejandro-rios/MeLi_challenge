package com.alejandrorios.meli_challenge.mappers

import com.alejandrorios.meli_challenge.data.entities.APIProduct
import com.alejandrorios.meli_challenge.data.entities.APIProductSeller
import com.alejandrorios.meli_challenge.data.entities.APIProductShipping
import com.alejandrorios.meli_challenge.data.mappers.APIProductMapper
import com.alejandrorios.meli_challenge.domain.models.Product
import com.alejandrorios.meli_challenge.domain.models.ProductSeller
import com.alejandrorios.meli_challenge.domain.models.ProductShipping
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class APIProductMapperTest {
    @Test
    fun `When mapAsProduct is called then should return Product`() {
        val apiShipping = APIProductShipping(freeShipping = true, storePickUp = false)
        val apiSeller = APIProductSeller(123456, "product seller nickname")
        val apiModel = APIProduct(
            id = "MCO1345000977",
            title = "Apple iPhone 15 (128 Gb) - Verde",
            price = 3433749,
            originalPrice = 2000000,
            thumbnail = "http://http2.mlstatic.com/D_767731-MLA71782898424_092023-I.jpg",
            shipping = apiShipping,
            seller = apiSeller,
        )

        val result = APIProductMapper.mapAsProduct(apiModel)

        result shouldBeEqualTo Product(
            id = "MCO1345000977",
            title = "Apple iPhone 15 (128 Gb) - Verde",
            price = 3433749,
            originalPrice = 2000000,
            thumbnail = "http://http2.mlstatic.com/D_767731-MLA71782898424_092023-I.jpg",
            shipping = ProductShipping(freeShipping = true, storePickUp = false),
            seller = ProductSeller(123456, "product seller nickname"),
        )
    }
}
