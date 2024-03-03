package com.alejandrorios.meli_challenge.ui.screens.product_detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.alejandrorios.meli_challenge.R
import com.alejandrorios.meli_challenge.ui.components.ErrorView
import com.alejandrorios.meli_challenge.ui.components.LoadingView
import com.alejandrorios.meli_challenge.ui.components.ProductDetailView
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    navController: NavController,
    productId: String,
    viewModel: ProductDetailViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(productId) {
        viewModel.getProductDetail(productId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_arrow_description)
                        )
                    }
                },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
    ) { paddingValues ->
        when {
            uiState.isLoading -> LoadingView(
                modifier = Modifier
                    .padding(paddingValues)
            )

            !uiState.errorMessage.isNullOrBlank() -> ErrorView(
                modifier = Modifier.padding(paddingValues),
                onRetry = {
                    viewModel.getProductDetail(productId)
                },
            )

            else -> ProductDetailView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                productDetails = uiState.productDetails!!,
                productDescription = uiState.productDescription,
                onBuyNowClicked = {
                    scope.launch {
                        snackBarHostState.showSnackbar(context.resources.getString(R.string.buy_now_message))
                    }
                },
                onAddCartClicked = {
                    scope.launch {
                        snackBarHostState.showSnackbar(context.resources.getString(R.string.added_to_cart))
                    }
                }
            )
        }
    }
}
