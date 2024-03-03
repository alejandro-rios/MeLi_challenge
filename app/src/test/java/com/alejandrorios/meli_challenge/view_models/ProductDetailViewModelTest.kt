package com.alejandrorios.meli_challenge.view_models

import app.cash.turbine.test
import com.alejandrorios.meli_challenge.data.utils.CallResponse
import com.alejandrorios.meli_challenge.data.utils.NetworkErrorException
import com.alejandrorios.meli_challenge.domain.models.ProductDescription
import com.alejandrorios.meli_challenge.domain.models.ProductDetail
import com.alejandrorios.meli_challenge.domain.repository.MeliRepository
import com.alejandrorios.meli_challenge.ui.screens.product_detail.ProductDetailViewModel
import com.alejandrorios.meli_challenge.utils.MainDispatcherRule
import com.alejandrorios.meli_challenge.utils.MockkableTest
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldBeTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProductDetailViewModelTest : MockkableTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    lateinit var repositoryMock: MeliRepository

    private lateinit var viewModel: ProductDetailViewModel
    private val result = mockk<ProductDetail>(relaxed = true)
    private val productDescription = ProductDescription(description = "This is a text description")

    @Before
    override fun setUp() {
        super.setUp()
        viewModel = ProductDetailViewModel(repositoryMock)
    }

    @Test
    fun `Should set update productDetails when getProductDetail is invoked`() = runTest {
        coEvery {
            repositoryMock.getProductDetails("ABC123")
        } answers {
            CallResponse.success(result)
        }

        coEvery {
            repositoryMock.getProductDescription("ABC123")
        } answers {
            CallResponse.success(productDescription)
        }

        viewModel.uiState.test {
            viewModel.getProductDetail(productId = "ABC123")
            val loadingStep = awaitItem()

            loadingStep.isLoading.shouldBeTrue()
            loadingStep.errorMessage.shouldBeNull()

            val resultStep = awaitItem()

            resultStep.isLoading.shouldBeFalse()
            resultStep.errorMessage.shouldBeNull()
            resultStep.productDetails shouldBeEqualTo result
            resultStep.productDescription shouldBeEqualTo productDescription.description
        }
    }

    @Test
    fun `Should set update error message when getProductDetail is invoked and fails`() = runTest {
        coEvery {
            repositoryMock.getProductDetails("BADC123")
        } answers {
            CallResponse.failure<NetworkErrorException>(NetworkErrorException("An error occurred"))
        }

        viewModel.uiState.test {
            viewModel.getProductDetail(productId = "BADC123")
            val loadingStep = awaitItem()

            loadingStep.isLoading.shouldBeTrue()
            loadingStep.errorMessage.shouldBeNull()

            val resultStep = awaitItem()
            resultStep.isLoading.shouldBeFalse()
            resultStep.errorMessage shouldBeEqualTo "An error occurred"
            resultStep.productDetails.shouldBeNull()
            resultStep.productDescription shouldBeEqualTo ""
        }
    }

    @Test
    fun `Should set update error message when getProductDescription is invoked and fails `() = runTest {
        coEvery {
            repositoryMock.getProductDetails("BABDC123")
        } answers {
            CallResponse.success(result)
        }

        coEvery {
            repositoryMock.getProductDescription("BABDC123")
        } answers {
            CallResponse.failure<NetworkErrorException>(NetworkErrorException("An error occurred"))
        }

        viewModel.uiState.test {
            viewModel.getProductDetail(productId = "BABDC123")
            val loadingStep = awaitItem()

            loadingStep.isLoading.shouldBeTrue()
            loadingStep.errorMessage.shouldBeNull()

            val resultStep = awaitItem()
            resultStep.isLoading.shouldBeFalse()
            resultStep.errorMessage shouldBeEqualTo "An error occurred"
            resultStep.productDetails.shouldBeNull()
            resultStep.productDescription shouldBeEqualTo ""
        }
    }
}


