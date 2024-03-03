package com.alejandrorios.meli_challenge.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alejandrorios.meli_challenge.R
import com.alejandrorios.meli_challenge.domain.models.ProductDetail
import com.alejandrorios.meli_challenge.domain.models.ProductShipping
import com.alejandrorios.meli_challenge.ui.theme.BlueMeLi
import com.alejandrorios.meli_challenge.ui.theme.GrayMeLi
import com.alejandrorios.meli_challenge.ui.theme.GreenMeLi
import com.alejandrorios.meli_challenge.ui.theme.MeLi_challengeTheme
import com.alejandrorios.meli_challenge.utils.getDiscount
import com.alejandrorios.meli_challenge.utils.toCurrencyString

@Composable
fun ProductDetailView(
    modifier: Modifier = Modifier,
    productDetails: ProductDetail,
    productDescription: String,
    onBuyNowClicked: () -> Unit = {},
    onAddCartClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = productDetails.title, maxLines = 2)
        VerticalSpacer()
        if (productDetails.pictures?.isNotEmpty() == true) {
            PictureSlider(pictures = productDetails.pictures)
        } else {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(productDetails.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.product_thumb_description),
                placeholder = painterResource(R.drawable.placeholder),
                alignment = Alignment.CenterStart,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        VerticalSpacer()
        productDetails.originalPrice?.let {
            Text(
                text = it.toCurrencyString(),
                fontSize = 14.sp,
                color = GrayMeLi,
                textDecoration = TextDecoration.LineThrough,
            )
            VerticalSpacer(height = 5.dp)
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = productDetails.price.toCurrencyString(), fontSize = 32.sp)
            productDetails.originalPrice?.let {
                HorizontalSpacer()
                Text(
                    text = stringResource(id = R.string.discount, it.getDiscount(productDetails.price)),
                    fontSize = 20.sp,
                    color = GreenMeLi
                )
                VerticalSpacer(height = 5.dp)
            }
        }
        if (productDetails.shipping.freeShipping) {
            Text(
                text = stringResource(id = R.string.free_shipping),
                color = GreenMeLi
            )
            VerticalSpacer()
        }
        Text(text = productDetails.warranty)
        VerticalSpacer(height = 20.dp)
        Divider(thickness = 1.dp)
        VerticalSpacer(height = 20.dp)
        Text(text = stringResource(R.string.details), fontSize = 20.sp)
        VerticalSpacer(height = 16.dp)
        ExpandableText(
            text = productDescription,
            fontSize = 16.sp,
            collapsedMaxLine = 20,
            showMoreText = stringResource(R.string.show_more),
            showMoreStyle = SpanStyle(color = BlueMeLi),
            showLessText = stringResource(R.string.show_less),
            showLessStyle = SpanStyle(color = BlueMeLi),
        )
        VerticalSpacer(height = 20.dp)
        Button(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(6.dp), onClick = onBuyNowClicked) {
            Text(text = stringResource(R.string.buy_now))
        }
        VerticalSpacer()
        Button(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(6.dp), onClick = onAddCartClicked) {
            Text(text = stringResource(R.string.add_to_cart))
        }
        VerticalSpacer()
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailViewPreview() {
    MeLi_challengeTheme {
        ProductDetailView(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            productDetails = ProductDetail(
                id = "MCO1345000977",
                siteId = "MCO",
                title = "Apple iPhone 15 (128 Gb) - Verde",
                price = 3433749,
                basePrice = 3433749,
                originalPrice = 2000000,
                availableQuantity = 3,
                thumbnail = "http://http2.mlstatic.com/D_767731-MLA71782898424_092023-I.jpg",
                permalink = "https://articulo.mercadolibre.com.co/MCO-1345000977-apple-iphone-15-128-gb-verde-_JM",
                warranty = "Garantía de fábrica: 12 meses",
                pictures = null,
                shipping = ProductShipping(freeShipping = true, storePickUp = false)
            ),
            productDescription = stringResource(id = R.string.lorem_ipsum),
        )
    }
}
