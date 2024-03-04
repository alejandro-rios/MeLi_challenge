package com.alejandrorios.meli_challenge

import com.alejandrorios.meli_challenge.data.utils.CallResponse
import com.alejandrorios.meli_challenge.data.utils.NetworkErrorException
import com.alejandrorios.meli_challenge.data.utils.handleResponse
import com.alejandrorios.meli_challenge.utils.MockKableTest
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import java.util.Locale
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Call
import retrofit2.Response
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

class HandleResponseExtensionTest : MockKableTest {

    private val continuation = mockk<Continuation<CallResponse<String>>>()
    private val call = mockk<Call<String>>()

    @Test
    fun `given continuation when handleResponse it's invoked then should get a String`() = runBlocking {
        val callResponse = mockk<Response<String>>()

        every { call.execute() } returns callResponse
        every { callResponse.isSuccessful } returns true
        every { callResponse.errorBody() } returns null
        every { callResponse.body() } returns "this is a test"
        every { continuation.resume(any()) } just Runs

        callResponse.handleResponse(continuation) { it.uppercase(Locale.getDefault()) }

        verify { continuation.resume(CallResponse.success("THIS IS A TEST")) }
    }

    @Test
    fun `given continuation when handleResponse it's invoked with errorBody then should get a NetworkErrorException`() =
        runBlocking {
            val callResponse = mockk<Response<String>>()
            val errorBody = "error response"

            every { call.execute() } returns callResponse
            every { callResponse.isSuccessful } returns true
            every { callResponse.errorBody()?.string() } returns errorBody
            every {
                continuation.resume(
                    match {
                        it is CallResponse.Failure &&
                                it.t is NetworkErrorException &&
                                it.t.message == errorBody
                    }
                )
            } just Runs

            callResponse.handleResponse(continuation) { it.uppercase(Locale.getDefault()) }

            verify {
                continuation.resume(
                    match {
                        it is CallResponse.Failure &&
                                it.t is NetworkErrorException &&
                                it.t.message == errorBody
                    }
                )
            }
        }

    @Test
    fun `given continuation when handleResponse it's invoked with body as null then should get a Throwable`() = runBlocking {
        val callResponse = mockk<Response<String>>()

        every { call.execute() } returns callResponse
        every { callResponse.isSuccessful } returns true
        every { callResponse.errorBody() } returns null
        every { callResponse.body() } returns null
        every {
            continuation.resume(
                match {
                    it is CallResponse.Failure && it.t.message == "Unknown error"
                }
            )
        } just Runs

        callResponse.handleResponse(continuation) { it.uppercase(Locale.getDefault()) }

        verify {
            continuation.resume(
                match {
                    it is CallResponse.Failure && it.t.message == "Unknown error"
                }
            )
        }
    }
}

