package com.alejandrorios.meli_challenge.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.alejandrorios.meli_challenge.domain.models.Product

@Composable
fun ProductListView(
    products: List<Product>,
    isLoadingProducts: Boolean,
    onClickProduct: (String) -> Unit,
    onNewItems: () -> Unit,
) {
    val listState = rememberLazyListState()
    val reachedBottom: Boolean by remember { derivedStateOf { listState.reachedBottom() } }

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.testTag("product_list")
    ) {
        itemsIndexed(products) { _, item: Product ->
            ProductTile(product = item) {
                onClickProduct(item.id)
            }
        }

        if (isLoadingProducts) item {
            CircularProgressIndicator(modifier = Modifier.padding(vertical = 16.dp))
        }
    }

    LaunchedEffect(reachedBottom) {
        if (reachedBottom) onNewItems()
    }
}

/***
 * Resource:
 * https://medium.com/@giorgos.patronas1/endless-scrolling-in-android-with-jetpack-compose-af1f55a03d1a
 */
internal fun LazyListState.reachedBottom(buffer: Int = 1): Boolean {
    val lastVisibleItem = this.layoutInfo.visibleItemsInfo.lastOrNull()
    return lastVisibleItem?.index != 0 && lastVisibleItem?.index == this.layoutInfo.totalItemsCount - buffer
}

