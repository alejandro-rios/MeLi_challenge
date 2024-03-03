package com.alejandrorios.meli_challenge.ui.screens.search_product

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alejandrorios.meli_challenge.R
import com.alejandrorios.meli_challenge.ui.components.EmptyView
import com.alejandrorios.meli_challenge.ui.components.ErrorView
import com.alejandrorios.meli_challenge.ui.components.HorizontalSpacer
import com.alejandrorios.meli_challenge.ui.components.LoadingView
import com.alejandrorios.meli_challenge.ui.components.ProductListView
import com.alejandrorios.meli_challenge.ui.navigation.NavigationItem
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchProductScreen(
    navController: NavController,
    viewModel: SearchProductViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchHistory = remember { mutableStateListOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        SearchBar(
            query = uiState.searchQuery,
            onQueryChange = viewModel::onSearchTextChange,
            onSearch = {
                keyboardController?.hide()
                if (!searchHistory.contains(it)) {
                    searchHistory.add(it)
                }
                viewModel.searchProduct(it)
            },
            active = uiState.isActive,
            onActiveChange = viewModel::onToggleSearch,
            placeholder = {
                Text(text = stringResource(R.string.search_hint))
            },
            leadingIcon = {
                if (uiState.isActive) {
                    IconButton(onClick = { viewModel.onToggleSearch(false) }) {
                        Icon(
                            Icons.Rounded.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                } else {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                }
            },
            trailingIcon = {
                if (uiState.isActive)
                    IconButton(onClick = viewModel::onSearchTextClear) {
                        Icon(
                            Icons.Rounded.Clear,
                            contentDescription = stringResource(R.string.clear_search)
                        )
                    }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .testTag("search_input"),
        ) {
            searchHistory.forEach {
                if (it.isNotEmpty()) {
                    Row(modifier = Modifier
                        .padding(all = 14.dp)
                        .fillMaxWidth()
                        .clickable { viewModel.searchProduct(it) }) {
                        Icon(imageVector = Icons.Default.History, contentDescription = null)
                        HorizontalSpacer()
                        Text(text = it)
                    }
                }
            }
        }

        when {
            uiState.isEmpty -> EmptyView(modifier = Modifier.padding(horizontal = 16.dp))
            uiState.isLoading -> LoadingView()
            !uiState.errorMessage.isNullOrBlank() -> ErrorView(
                modifier = Modifier.padding(16.dp),
                message = stringResource(id = R.string.error_message_search)
            )

            else -> ProductListView(
                products = uiState.products,
                isLoadingProducts = uiState.isLoadingProducts,
                onClickProduct = { productId ->
                    navController.navigate("${NavigationItem.ProductDetails.route}/$productId")
                },
                onNewItems = viewModel::loadMoreResults
            )
        }
    }
}
