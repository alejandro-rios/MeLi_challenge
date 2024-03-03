package com.alejandrorios.meli_challenge.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alejandrorios.meli_challenge.R
import com.alejandrorios.meli_challenge.domain.models.ProductPicture
import com.alejandrorios.meli_challenge.ui.theme.MeLi_challengeTheme

/***
 * Resources:
 * https://medium.com/@dheerubhadoria/building-a-simple-image-slider-in-jetpack-compose-for-android-24ad49e706e4
 * https://www.youtube.com/watch?v=ro3a-GmaLqE
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PictureSlider(modifier: Modifier = Modifier, pictures: List<ProductPicture>) {
    val pagerState = rememberPagerState(pageCount = { pictures.count() })
    val currentImageIndex = pagerState.currentPage + 1

    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        HorizontalPager(state = pagerState, key = { pictures[it].id }, pageSize = PageSize.Fill) { index ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pictures[index].url)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.picture_slider_description, currentImageIndex),
                placeholder = painterResource(R.drawable.placeholder),
                alignment = Alignment.Center,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .background(color = Color.White)
                    .fillMaxWidth()
            )
        }
        Box(modifier = Modifier.padding(start = 16.dp, top = 16.dp)) {
            Text(
                text = "$currentImageIndex/${pictures.count()}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(color = Color.LightGray, shape = RoundedCornerShape(10.dp))
                    .padding(horizontal = 6.dp, vertical = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PictureSliderPreview() {
    MeLi_challengeTheme {
        PictureSlider(pictures = listOf(ProductPicture("", ""), ProductPicture("", "")))
    }
}
