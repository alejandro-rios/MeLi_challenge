package com.alejandrorios.meli_challenge.di

import com.alejandrorios.meli_challenge.data.repository.MeliRepositoryImpl
import com.alejandrorios.meli_challenge.domain.repository.MeliRepository
import org.koin.dsl.module

val domainModule = module {
    // Repository
    single<MeliRepository> { MeliRepositoryImpl(get()) }
}
