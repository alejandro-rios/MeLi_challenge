package com.alejandrorios.meli_challenge.mappers

import com.alejandrorios.meli_challenge.data.entities.APIProductDetail
import com.alejandrorios.meli_challenge.data.entities.APIProductPicture
import com.alejandrorios.meli_challenge.data.entities.APIProductShipping
import com.alejandrorios.meli_challenge.data.mappers.APIProductDetailMapper
import com.alejandrorios.meli_challenge.domain.models.ProductDetail
import com.alejandrorios.meli_challenge.domain.models.ProductPicture
import com.alejandrorios.meli_challenge.domain.models.ProductShipping
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class APIProductDetailMapperTest {
    @Test
    fun `When mapAsProductDetail is called then should return ProductDetail`() {
        val apiShipping = APIProductShipping(freeShipping = true, storePickUp = false)
        val apiPictures = listOf(APIProductPicture("pictureId", "pictureUrl"))
        val apiModel = APIProductDetail(
            id = "MCO1345000977",
            siteId = "MCO",
            title = "Apple iPhone 15 (128 Gb) - Verde",
            price = 3433749,
            basePrice = 3433749,
            originalPrice = 2000000,
            availableQuantity = 3,
            thumbnail = "http://http2.mlstatic.com/D_767731-MLA71782898424_092023-I.jpg",
            permalink = "https://articulo.mercadolibre.com.co/MCO-1345000977-apple-iphone-15-128-gb-verde-_JM",
            warranty = "Garantía de fábrica: 12 meses",
            pictures = apiPictures,
            shipping = apiShipping,
        )

        val result = APIProductDetailMapper.mapAsProductDetail(apiModel)

        result shouldBeEqualTo ProductDetail(
            id = "MCO1345000977",
            siteId = "MCO",
            title = "Apple iPhone 15 (128 Gb) - Verde",
            price = 3433749,
            basePrice = 3433749,
            originalPrice = 2000000,
            availableQuantity = 3,
            thumbnail = "http://http2.mlstatic.com/D_767731-MLA71782898424_092023-I.jpg",
            permalink = "https://articulo.mercadolibre.com.co/MCO-1345000977-apple-iphone-15-128-gb-verde-_JM",
            warranty = "Garantía de fábrica: 12 meses",
            pictures = listOf(ProductPicture("pictureId", "pictureUrl")),
            shipping = ProductShipping(freeShipping = true, storePickUp = false),
        )
    }
}
