package com.alejandrorios.meli_challenge.mappers

import com.alejandrorios.meli_challenge.data.entities.APISearchPaging
import com.alejandrorios.meli_challenge.data.mappers.APISearchPagingMapper
import com.alejandrorios.meli_challenge.domain.models.SearchPaging
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class APISearchPagingMapperTest {
    @Test
    fun `When mapAsSearchPaging is called then should return SearchPaging`() {
        val apiModel = APISearchPaging(85, 42, 0, 10)

        val result = APISearchPagingMapper.mapAsSearchPaging(apiModel)

        result shouldBeEqualTo SearchPaging(85, 42, 0, 10)
    }
}
