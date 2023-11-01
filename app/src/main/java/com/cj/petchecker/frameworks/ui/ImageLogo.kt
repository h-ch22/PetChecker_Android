package com.cj.petchecker.frameworks.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cj.petchecker.R

@Composable
fun ImageLogo(
    elevation: Dp = 15.dp, imageSize: Dp = 250.dp
){
    Card(
        modifier = Modifier.wrapContentSize(), elevation = CardDefaults.elevatedCardElevation(elevation)
    ) {
        Image(painter = painterResource(id = R.drawable.ic_appstore), contentDescription = null, modifier = Modifier
            .size(imageSize)
        )
    }
}