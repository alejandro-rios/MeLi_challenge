package com.alejandrorios.meli_challenge.data.network

import com.alejandrorios.meli_challenge.data.entities.APIProductDetail
import com.alejandrorios.meli_challenge.data.entities.APIProductDescription
import com.alejandrorios.meli_challenge.data.entities.APISearchResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MeliAPIService {

    @GET(SEARCH_PATH)
    fun searchProduct(@Query("q") query: String, @Query("limit") limit: Int, @Query("offset") offset: Int): Call<APISearchResults>

    @GET(ITEM_PATH)
    fun getProductDetails(@Path("id") id: String): Call<APIProductDetail>

    @GET(ITEM_DESCRIPTION_PATH)
    fun getProductDescription(@Path("id") id: String): Call<APIProductDescription>
}
