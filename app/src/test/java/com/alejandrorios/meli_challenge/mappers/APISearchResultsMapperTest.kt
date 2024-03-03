package com.alejandrorios.meli_challenge.mappers

import com.alejandrorios.meli_challenge.data.entities.APIProduct
import com.alejandrorios.meli_challenge.data.entities.APIProductSeller
import com.alejandrorios.meli_challenge.data.entities.APIProductShipping
import com.alejandrorios.meli_challenge.data.entities.APISearchPaging
import com.alejandrorios.meli_challenge.data.entities.APISearchResults
import com.alejandrorios.meli_challenge.data.mappers.APISearchResultsMapper
import com.alejandrorios.meli_challenge.domain.models.Product
import com.alejandrorios.meli_challenge.domain.models.ProductSeller
import com.alejandrorios.meli_challenge.domain.models.ProductShipping
import com.alejandrorios.meli_challenge.domain.models.SearchPaging
import com.alejandrorios.meli_challenge.domain.models.SearchResults
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class APISearchResultsMapperTest {
    @Test
    fun `When mapAsSearchResults is called then should return SearchResults`() {
        val apiPaging = APISearchPaging(85, 42, 0, 10)
        val apiShipping = APIProductShipping(freeShipping = true, storePickUp = false)
        val apiSeller = APIProductSeller(123456, "product seller nickname")
        val apiProduct = listOf(
            APIProduct(
                id = "MCO1345000977",
                title = "Apple iPhone 15 (128 Gb) - Verde",
                price = 3433749,
                originalPrice = 2000000,
                thumbnail = "http://http2.mlstatic.com/D_767731-MLA71782898424_092023-I.jpg",
                shipping = apiShipping,
                seller = apiSeller,
            )
        )
        val apiModel = APISearchResults(
            siteId = "MCO",
            query = "iPhone",
            paging = apiPaging,
            results = apiProduct,
        )

        val result = APISearchResultsMapper.mapAsSearchResults(apiModel)

        result shouldBeEqualTo SearchResults(
            siteId = "MCO",
            query = "iPhone",
            paging = SearchPaging(85, 42, 0, 10),
            results = listOf(
                Product(
                    id = "MCO1345000977",
                    title = "Apple iPhone 15 (128 Gb) - Verde",
                    price = 3433749,
                    originalPrice = 2000000,
                    thumbnail = "http://http2.mlstatic.com/D_767731-MLA71782898424_092023-I.jpg",
                    shipping = ProductShipping(freeShipping = true, storePickUp = false),
                    seller = ProductSeller(123456, "product seller nickname"),
                )
            )
        )
    }
}
