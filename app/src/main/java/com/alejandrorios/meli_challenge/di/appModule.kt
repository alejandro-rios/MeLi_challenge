package com.alejandrorios.meli_challenge.di

import com.alejandrorios.meli_challenge.ui.screens.search_product.SearchProductViewModel
import com.alejandrorios.meli_challenge.ui.screens.product_detail.ProductDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // ViewModels
    viewModel { SearchProductViewModel(get()) }
    viewModel { ProductDetailViewModel(get()) }
}
