package com.cj.petchecker.inspection.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Adjust
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cj.petchecker.ui.theme.PetCheckerColorPalette
import com.cj.petchecker.ui.theme.PetCheckerTheme
import com.cj.petchecker.ui.theme.gray

@Composable
fun InspectionMainView(){
    PetCheckerTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = PetCheckerColorPalette.current.background) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
                    Icon(imageVector = Icons.Rounded.Search, contentDescription = null, tint = PetCheckerColorPalette.current.txtColor)

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(text = "검사 시작하기", fontWeight = FontWeight.SemiBold, color = PetCheckerColorPalette.current.txtColor, fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
                    Icon(imageVector = Icons.Rounded.Adjust, contentDescription = null, tint = PetCheckerColorPalette.current.txtColor)

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(text = "딥러닝 모델", fontWeight = FontWeight.SemiBold, color = PetCheckerColorPalette.current.txtColor, fontSize = 15.sp)
                        Text(text = "사전 학습된 딥러닝 모델을 통해 빠르고 정밀한 검사 결과를 확인할 수 있습니다.", color = gray, fontSize = 12.sp, lineHeight = 15.sp)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
                    Icon(imageVector = Icons.Rounded.Adjust, contentDescription = null, tint = PetCheckerColorPalette.current.txtColor)

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(text = "발병 부위 확인", fontWeight = FontWeight.SemiBold, color = PetCheckerColorPalette.current.txtColor, fontSize = 15.sp)
                        Text(text = "Attention HeatMap으로 발병된 부위를 시각적으로 확인할 수 있습니다.", color = gray, fontSize = 12.sp, lineHeight = 15.sp)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
                    Icon(imageVector = Icons.Rounded.Adjust, contentDescription = null, tint = PetCheckerColorPalette.current.txtColor)

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(text = "추세", fontWeight = FontWeight.SemiBold, color = PetCheckerColorPalette.current.txtColor, fontSize = 15.sp)
                        Text(text = "검사 기록을 시각적으로 확인할 수 있습니다.", color = gray, fontSize = 12.sp, lineHeight = 15.sp)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Spacer(modifier = Modifier.weight(1f))


            }
        }
    }
}

@Preview
@Composable
fun InspectionMainViewPreview(){
    InspectionMainView()
}