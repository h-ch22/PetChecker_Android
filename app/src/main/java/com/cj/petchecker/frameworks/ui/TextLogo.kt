package com.cj.petchecker.frameworks.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.cj.petchecker.R
import com.cj.petchecker.ui.theme.PetCheckerTheme

@Composable
fun TextLogo(fontSize: TextUnit = 18.sp){
    val footer = " Checker"
    val appName = stringResource(id = R.string.app_name, footer)
    val spanStyles = listOf(
        AnnotatedString.Range(
            SpanStyle(fontWeight = FontWeight.Bold),
            start = 0,
            end = 3
        )
    )

    PetCheckerTheme {
        Text(text = AnnotatedString(text = appName, spanStyles = spanStyles), fontSize = fontSize)
    }
}