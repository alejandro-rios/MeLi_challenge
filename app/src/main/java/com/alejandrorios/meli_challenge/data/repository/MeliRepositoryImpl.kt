package com.alejandrorios.meli_challenge.data.repository

import com.alejandrorios.meli_challenge.data.utils.CallResponse
import com.alejandrorios.meli_challenge.data.utils.NetworkErrorException
import com.alejandrorios.meli_challenge.data.entities.APIProductDetail
import com.alejandrorios.meli_challenge.data.entities.APIProductDescription
import com.alejandrorios.meli_challenge.data.entities.APISearchResults
import com.alejandrorios.meli_challenge.data.utils.handleResponse
import com.alejandrorios.meli_challenge.data.mappers.APIProductDescriptionMapper
import com.alejandrorios.meli_challenge.data.mappers.APIProductDetailMapper
import com.alejandrorios.meli_challenge.data.mappers.APISearchResultsMapper
import com.alejandrorios.meli_challenge.data.network.MeliAPIService
import com.alejandrorios.meli_challenge.domain.models.ProductDetail
import com.alejandrorios.meli_challenge.domain.models.ProductDescription
import com.alejandrorios.meli_challenge.domain.models.SearchResults
import com.alejandrorios.meli_challenge.domain.repository.MeliRepository
import retrofit2.Call
import retrofit2.Callback
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MeliRepositoryImpl(private val service: MeliAPIService) : MeliRepository {

    override suspend fun searchProduct(
        searchQuery: String,
        limit: Int,
        offset: Int
    ): CallResponse<SearchResults> = suspendCoroutine { continuation ->
        val call = service.searchProduct(query = searchQuery, limit = limit, offset = offset)

        call.enqueue(object : Callback<APISearchResults> {
            override fun onResponse(call: Call<APISearchResults>, response: retrofit2.Response<APISearchResults>) {
                response.handleResponse(
                    continuation = continuation,
                    transformation = { apiResponse ->
                        APISearchResultsMapper.mapAsSearchResults(apiResponse)
                    }
                )
            }

            override fun onFailure(call: Call<APISearchResults>, t: Throwable) {
                continuation.resume(CallResponse.failure<NetworkErrorException>(t.fillInStackTrace()))
            }
        })
    }

    override suspend fun getProductDetails(productId: String): CallResponse<ProductDetail> = suspendCoroutine { continuation ->
        val call = service.getProductDetails(id = productId)

        call.enqueue(object : Callback<APIProductDetail> {
            override fun onResponse(call: Call<APIProductDetail>, response: retrofit2.Response<APIProductDetail>) {
                response.handleResponse(
                    continuation = continuation,
                    transformation = { apiResponse ->
                        APIProductDetailMapper.mapAsProductDetail(apiResponse)
                    }
                )
            }

            override fun onFailure(call: Call<APIProductDetail>, t: Throwable) {
                continuation.resume(CallResponse.failure<NetworkErrorException>(t.fillInStackTrace()))
            }
        })
    }

    override suspend fun getProductDescription(productId: String): CallResponse<ProductDescription> =
        suspendCoroutine { continuation ->
            val call = service.getProductDescription(id = productId)

            call.enqueue(object : Callback<APIProductDescription> {
                override fun onResponse(
                    call: Call<APIProductDescription>,
                    response: retrofit2.Response<APIProductDescription>
                ) {
                    response.handleResponse(
                        continuation = continuation,
                        transformation = { apiResponse ->
                            APIProductDescriptionMapper.mapAsProductDescription(apiResponse)
                        }
                    )
                }

                override fun onFailure(call: Call<APIProductDescription>, t: Throwable) {
                    continuation.resume(CallResponse.failure<NetworkErrorException>(t.fillInStackTrace()))
                }
            })
        }
}
