package com.alejandrorios.meli_challenge.repository

import com.alejandrorios.meli_challenge.data.entities.APIProductDescription
import com.alejandrorios.meli_challenge.data.entities.APIProductDetail
import com.alejandrorios.meli_challenge.data.entities.APISearchResults
import com.alejandrorios.meli_challenge.data.network.MeliAPIService
import com.alejandrorios.meli_challenge.data.repository.MeliRepositoryImpl
import com.alejandrorios.meli_challenge.data.utils.CallResponse
import com.alejandrorios.meli_challenge.data.utils.NetworkErrorException
import com.alejandrorios.meli_challenge.utils.MockKableTest
import io.mockk.CapturingSlot
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MeliRepositoryTest : MockKableTest {

    @MockK
    lateinit var meliAPIService: MeliAPIService

    private lateinit var repository: MeliRepositoryImpl

    @Before
    override fun setUp() {
        super.setUp()
        repository = MeliRepositoryImpl(meliAPIService)
    }

    @Test
    fun `given repository when searchProduct it's called then should get a APISearchResults`() {
        val call = mockk<Call<APISearchResults>>()
        val responseData = mockk<Response<APISearchResults>>(relaxed = true)
        val slot = CapturingSlot<Callback<APISearchResults>>()

        coEvery {
            meliAPIService.searchProduct("product", 10, 0)
        } returns call

        coEvery {
            responseData.isSuccessful
        } returns true

        coEvery {
            responseData.errorBody()
        } returns null

        coEvery {
            responseData.body()
        } returns mockk(relaxed = true)

        every { call.enqueue(capture(slot)) } answers {
            slot.captured.onResponse(call, responseData)
        }

        runBlocking {
            repository.searchProduct("product", 10, 0)
        } shouldBeInstanceOf CallResponse.Success::class

        coVerify {
            meliAPIService.searchProduct("product", 10, 0)
            call.enqueue(any())
        }
    }

    @Test
    fun `given repository when getProductDetails it's called then should get a APIProductDetail`() {
        val call = mockk<Call<APIProductDetail>>()
        val responseData = mockk<Response<APIProductDetail>>(relaxed = true)
        val slot = CapturingSlot<Callback<APIProductDetail>>()

        coEvery {
            meliAPIService.getProductDetails("someProductId")
        } returns call

        coEvery {
            responseData.isSuccessful
        } returns true

        coEvery {
            responseData.errorBody()
        } returns null

        coEvery {
            responseData.body()
        } returns mockk(relaxed = true)

        every { call.enqueue(capture(slot)) } answers {
            slot.captured.onResponse(call, responseData)
        }

        runBlocking {
            repository.getProductDetails("someProductId")
        } shouldBeInstanceOf CallResponse.Success::class

        coVerify {
            meliAPIService.getProductDetails("someProductId")
            call.enqueue(any())
        }
    }

    @Test
    fun `given repository when getProductDescription it's called then should get a APIProductDescription`() {
        val call = mockk<Call<APIProductDescription>>()
        val responseData = mockk<Response<APIProductDescription>>(relaxed = true)
        val slot = CapturingSlot<Callback<APIProductDescription>>()

        coEvery {
            meliAPIService.getProductDescription("someProductId")
        } returns call

        coEvery {
            responseData.isSuccessful
        } returns true

        coEvery {
            responseData.errorBody()
        } returns null

        coEvery {
            responseData.body()
        } returns mockk(relaxed = true)

        every { call.enqueue(capture(slot)) } answers {
            slot.captured.onResponse(call, responseData)
        }

        runBlocking {
            repository.getProductDescription("someProductId")
        } shouldBeInstanceOf CallResponse.Success::class

        coVerify {
            meliAPIService.getProductDescription("someProductId")
            call.enqueue(any())
        }
    }

    @Test
    fun `given repository when searchProduct it's called and fails then should throw a NetworkErrorException`() {
        val call = mockk<Call<APISearchResults>>()
        val responseData = mockk<Response<APISearchResults>>(relaxed = true)
        val slot = CapturingSlot<Callback<APISearchResults>>()

        coEvery {
            meliAPIService.searchProduct("failed product", 10, 0)
        } returns call

        every { call.enqueue(capture(slot)) } answers {
            slot.captured.onFailure(call, NetworkErrorException("some message"))
        }

        coEvery {
            responseData.errorBody()
        } throws NetworkErrorException("some message")

        runBlocking {
            repository.searchProduct("failed product", 10, 0)
        } shouldBeInstanceOf CallResponse.Failure::class

        coVerify {
            meliAPIService.searchProduct("failed product", 10, 0)
            call.enqueue(any())
        }
    }

    @Test
    fun `given repository when getProductDetails it's called and fails then should throw a NetworkErrorException`() {
        val call = mockk<Call<APIProductDetail>>()
        val responseData = mockk<Response<APIProductDetail>>(relaxed = true)
        val slot = CapturingSlot<Callback<APIProductDetail>>()

        coEvery {
            meliAPIService.getProductDetails("badProductId")
        } returns call

        every { call.enqueue(capture(slot)) } answers {
            slot.captured.onFailure(call, NetworkErrorException("some message"))
        }

        coEvery {
            responseData.errorBody()
        } throws NetworkErrorException("some message")

        runBlocking {
            repository.getProductDetails("badProductId")
        } shouldBeInstanceOf CallResponse.Failure::class

        coVerify {
            meliAPIService.getProductDetails("badProductId")
            call.enqueue(any())
        }
    }

    @Test
    fun `given repository when getProductDescription it's called and fails then should throw a NetworkErrorException`() {
        val call = mockk<Call<APIProductDescription>>()
        val responseData = mockk<Response<APIProductDescription>>(relaxed = true)
        val slot = CapturingSlot<Callback<APIProductDescription>>()

        coEvery {
            meliAPIService.getProductDescription("badProductDescriptionId")
        } returns call

        every { call.enqueue(capture(slot)) } answers {
            slot.captured.onFailure(call, NetworkErrorException("some message"))
        }

        coEvery {
            responseData.errorBody()
        } throws NetworkErrorException("some message")

        runBlocking {
            repository.getProductDescription("badProductDescriptionId")
        } shouldBeInstanceOf CallResponse.Failure::class

        coVerify {
            meliAPIService.getProductDescription("badProductDescriptionId")
            call.enqueue(any())
        }
    }
}
