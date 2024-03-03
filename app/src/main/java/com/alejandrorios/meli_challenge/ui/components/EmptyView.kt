package com.alejandrorios.meli_challenge.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alejandrorios.meli_challenge.R
import com.alejandrorios.meli_challenge.ui.theme.MeLi_challengeTheme

@Composable
fun EmptyView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.search), contentDescription = "")
        VerticalSpacer()
        Text(stringResource(R.string.no_results), fontSize = 24.sp, fontWeight = FontWeight.Bold)
        VerticalSpacer()
        Text(
            stringResource(R.string.try_searching),
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyViewPreview() {
    MeLi_challengeTheme {
        EmptyView(modifier = Modifier.padding(horizontal = 16.dp))
    }
}
