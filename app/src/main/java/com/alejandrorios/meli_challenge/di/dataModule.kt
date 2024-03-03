package com.alejandrorios.meli_challenge.di

import com.alejandrorios.meli_challenge.data.network.BASE_URL
import com.alejandrorios.meli_challenge.data.network.MeliAPIService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit

private const val TIME_OUT = 30L

private val json = Json { ignoreUnknownKeys = true }

val dataModule = module {

    // Client
    single {
        OkHttpClient
            .Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    // Service
    single { createMeliApiService(get()) }
}

fun createMeliApiService(retrofit: Retrofit): MeliAPIService = retrofit.create(MeliAPIService::class.java)
