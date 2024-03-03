package com.alejandrorios.meli_challenge.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alejandrorios.meli_challenge.R
import com.alejandrorios.meli_challenge.domain.models.Product
import com.alejandrorios.meli_challenge.domain.models.ProductSeller
import com.alejandrorios.meli_challenge.domain.models.ProductShipping
import com.alejandrorios.meli_challenge.ui.theme.GrayMeLi
import com.alejandrorios.meli_challenge.ui.theme.GreenMeLi
import com.alejandrorios.meli_challenge.ui.theme.MeLi_challengeTheme
import com.alejandrorios.meli_challenge.utils.toCurrencyString

@Composable
fun ProductTile(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .background(color = Color.White)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product.thumbnail)
                        .crossfade(true)
                        .build(),
                    alignment = Alignment.CenterStart,
                    placeholder = painterResource(R.drawable.placeholder),
                    contentDescription = stringResource(R.string.product_tile_thumb_description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            HorizontalSpacer()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 10.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = product.title,
                    maxLines = 2,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    color = Color.Black
                )
                Text(
                    text = stringResource(id = R.string.seller, product.seller.nickname),
                    fontSize = 12.sp,
                    color = GrayMeLi,
                )
                VerticalSpacer(height = 8.dp)
                product.originalPrice?.let {
                    Text(
                        text = it.toCurrencyString(),
                        fontSize = 12.sp,
                        color = GrayMeLi,
                        textDecoration = TextDecoration.LineThrough,
                    )
                }
                Text(
                    text = product.price.toCurrencyString(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W400,
                    maxLines = 1,
                    color = Color.Black
                )
                if (product.shipping.freeShipping) {
                    Text(
                        text = stringResource(id = R.string.free_shipping),
                        color = GreenMeLi
                    )
                }
            }
        }
    }
}

@Preview(name = "Product tile with discount", showBackground = true)
@Composable
fun ProductTileWithDiscountPreview() {
    val product = Product(
        id = "id",
        title = "Apple iPhone 15 (128 Gb) - Verde",
        thumbnail = "",
        price = 200,
        originalPrice = 250,
        shipping = ProductShipping(freeShipping = true, storePickUp = false),
        seller = ProductSeller(123, "Pepito")
    )
    MeLi_challengeTheme {
        ProductTile(product = product, onClick = {})
    }
}

@Preview(name = "Product tile without discount", showBackground = true)
@Composable
fun ProductTileWithoutDiscountPreview() {
    val product = Product(
        id = "id",
        title = "Apple iPhone 15 (128 Gb) - Verde",
        thumbnail = "",
        price = 250,
        originalPrice = null,
        shipping = ProductShipping(freeShipping = true, storePickUp = false),
        seller = ProductSeller(123, "Pepito")
    )
    MeLi_challengeTheme {
        ProductTile(product = product, onClick = {})
    }
}

