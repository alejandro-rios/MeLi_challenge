package com.alejandrorios.meli_challenge.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.alejandrorios.meli_challenge.ui.navigation.AppNavHost
import com.alejandrorios.meli_challenge.ui.screens.search_product.SearchProductScreen
import com.alejandrorios.meli_challenge.ui.theme.MeLi_challengeTheme
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinExperimentalAPI

class MainActivity : ComponentActivity() {
    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoinAndroidContext {
                MeLi_challengeTheme {
                    AppNavHost(navController = rememberNavController())
                }
            }
        }
    }
}
