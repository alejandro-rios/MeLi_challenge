package com.alejandrorios.meli_challenge.view_models

import app.cash.turbine.test
import com.alejandrorios.meli_challenge.utils.MainDispatcherRule
import com.alejandrorios.meli_challenge.data.utils.CallResponse
import com.alejandrorios.meli_challenge.data.utils.NetworkErrorException
import com.alejandrorios.meli_challenge.domain.models.Product
import com.alejandrorios.meli_challenge.domain.models.SearchPaging
import com.alejandrorios.meli_challenge.domain.models.SearchResults
import com.alejandrorios.meli_challenge.domain.repository.MeliRepository
import com.alejandrorios.meli_challenge.ui.screens.search_product.SearchProductViewModel
import com.alejandrorios.meli_challenge.utils.MockkableTest
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchProductViewModelTest : MockkableTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    lateinit var repositoryMock: MeliRepository

    private lateinit var viewModel: SearchProductViewModel
    private val result = mockk<SearchResults>(relaxed = true)
    private val moreResults = mockk<SearchResults>(relaxed = true)
    private val productList = mockk<List<Product>>(relaxed = true)
    private val moreProducts = mockk<List<Product>>(relaxed = true)
    private val paging = SearchPaging(
        total = 20,
        primaryResults = 10,
        offset = 0,
        limit = 5
    )
    private val pagingMore = paging.copy(offset = 1)

    @Before
    override fun setUp() {
        super.setUp()
        viewModel = SearchProductViewModel(repositoryMock)
    }

    @Test
    fun `Should set update searchQuery when onSearchTextChange is invoked`() = runTest {
        viewModel.onSearchTextChange(query = "this is a test")

        advanceUntilIdle()

        viewModel.uiState.value.searchQuery shouldBeEqualTo "this is a test"
    }

    @Test
    fun `Should set update active when onToggleSearch is invoked`() = runTest {
        viewModel.onToggleSearch(active = true)

        advanceUntilIdle()

        viewModel.uiState.value.isActive.shouldBeTrue()

        viewModel.onToggleSearch(active = false)

        advanceUntilIdle()

        viewModel.uiState.value.isActive.shouldBeFalse()
    }

    @Test
    fun `Should set update searchQuery when onSearchTextClear is invoked`() = runTest {
        viewModel.onSearchTextChange(query = "search to clear")

        advanceUntilIdle()

        viewModel.uiState.value.searchQuery shouldBeEqualTo "search to clear"

        viewModel.onSearchTextClear()

        advanceUntilIdle()

        viewModel.uiState.value.searchQuery shouldBeEqualTo ""
    }

    @Test
    fun `Should set update products when searchProduct is invoked`() = runTest {
        coEvery {
            repositoryMock.searchProduct("product", 10, 0)
        } answers {
            CallResponse.success(result)
        }

        every {
            result.paging
        } returns paging

        every {
            result.results
        } returns productList

        viewModel.uiState.test {
            awaitItem()

            viewModel.searchProduct(query = "product")
            val loadingStep = awaitItem()

            loadingStep.isLoading.shouldBeTrue()
            loadingStep.isActive.shouldBeFalse()

            val resultStep = awaitItem()

            resultStep.searchQuery shouldBeEqualTo "product"
            resultStep.isLoading.shouldBeFalse()
            resultStep.isActive.shouldBeFalse()
            resultStep.productsTotal shouldBeEqualTo 20
            resultStep.products shouldBeEqualTo productList
        }
    }

    @Test
    fun `Should set update error message when searchProduct is invoked and fails`() = runTest {
        coEvery {
            repositoryMock.searchProduct("bad product", 10, 0)
        } answers {
            CallResponse.failure<NetworkErrorException>(NetworkErrorException("An error occurred"))
        }

        viewModel.uiState.test {
            awaitItem()

            viewModel.searchProduct(query = "bad product")
            val loadingStep = awaitItem()

            loadingStep.isLoading.shouldBeTrue()
            loadingStep.isActive.shouldBeFalse()

            val resultStep = awaitItem()

            resultStep.searchQuery shouldBeEqualTo ""
            resultStep.isLoading.shouldBeFalse()
            resultStep.isActive.shouldBeFalse()
            resultStep.productsTotal shouldBeEqualTo 0
            resultStep.errorMessage shouldBeEqualTo "An error occurred"
            resultStep.products shouldBeEqualTo emptyList()
        }
    }

    @Test
    fun `Should set update more products when loadMoreResults is invoked`() = runTest {
        coEvery {
            repositoryMock.searchProduct("product", 10, 0)
        } answers {
            CallResponse.success(result)
        }

        every {
            result.paging
        } returns paging

        every {
            result.results
        } returns productList

        every {
            productList.size
        } returns 10

        coEvery {
            repositoryMock.searchProduct("product", 10, 10)
        } answers {
            CallResponse.success(moreResults)
        }

        every {
            moreResults.paging
        } returns pagingMore

        every {
            moreResults.results
        } returns moreProducts

        viewModel.searchProduct(query = "product")

        advanceUntilIdle()

        viewModel.uiState.test {
            awaitItem()

            viewModel.loadMoreResults()
            val loadingStep = awaitItem()

            loadingStep.isLoadingProducts.shouldBeTrue()
            loadingStep.isLoading.shouldBeFalse()
            loadingStep.isActive.shouldBeFalse()

            val resultStep = awaitItem()

            resultStep.searchQuery shouldBeEqualTo "product"
            resultStep.isLoadingProducts.shouldBeFalse()
            resultStep.isLoading.shouldBeFalse()
            resultStep.isActive.shouldBeFalse()
            resultStep.productsTotal shouldBeEqualTo 20
            resultStep.products shouldBeEqualTo productList + moreProducts
        }
    }

    @Test
    fun `Should set update error message when loadMoreResults is invoked and fails`() = runTest {
        coEvery {
            repositoryMock.searchProduct("bad load more product", 10, 0)
        } answers {
            CallResponse.success(result)
        }

        every {
            result.paging
        } returns paging

        every {
            result.results
        } returns productList

        every {
            productList.size
        } returns 10

        coEvery {
            repositoryMock.searchProduct("bad load more product", 10, 10)
        } answers {
            CallResponse.failure<NetworkErrorException>(NetworkErrorException("An error occurred"))
        }

        every {
            productList.size
        } returns 10

        viewModel.searchProduct(query = "bad load more product")

        advanceUntilIdle()

        viewModel.uiState.test {
            awaitItem()

            viewModel.loadMoreResults()
            val loadingStep = awaitItem()

            loadingStep.isLoadingProducts.shouldBeTrue()
            loadingStep.isLoading.shouldBeFalse()
            loadingStep.isActive.shouldBeFalse()

            val resultStep = awaitItem()

            resultStep.isLoadingProducts.shouldBeFalse()
            resultStep.searchQuery shouldBeEqualTo "bad load more product"
            resultStep.isLoading.shouldBeFalse()
            resultStep.isActive.shouldBeFalse()
            resultStep.productsTotal shouldBeEqualTo 20
            resultStep.errorMessage shouldBeEqualTo "An error occurred"
            resultStep.products shouldBeEqualTo productList
        }
    }
}
