package com.cj.petchecker.more.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cj.petchecker.R
import com.cj.petchecker.ui.theme.PetCheckerColorPalette
import com.cj.petchecker.ui.theme.PetCheckerTheme
import com.cj.petchecker.ui.theme.accent
import com.cj.petchecker.ui.theme.blue
import com.cj.petchecker.userManagement.helper.UserManagement

@Composable
fun MoreView(){
    PetCheckerTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = PetCheckerColorPalette.current.background) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = { /*TODO*/ }) {
                    Card(modifier = Modifier
                        .fillMaxWidth(),
                        shape = RoundedCornerShape(15.dp),
                        colors = CardDefaults.cardColors(PetCheckerColorPalette.current.btnColor),
                        elevation = CardDefaults.cardElevation(15.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp)) {
                            Image(painter = painterResource(id = R.drawable.ic_appstore), contentDescription = null, modifier = Modifier
                                .clip(
                                    CircleShape
                                )
                                .size(30.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = UserManagement.getUserInfo()?.name ?: "사용자 정보 없음", color = PetCheckerColorPalette.current.txtColor, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Divider()

                Spacer(modifier = Modifier.height(10.dp))

                Card(modifier = Modifier
                    .fillMaxWidth(),
                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(PetCheckerColorPalette.current.btnColor),
                    elevation = CardDefaults.cardElevation(15.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp)) {
                        Icon(imageVector = Icons.Rounded.Notifications, contentDescription = null, tint = blue)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "공지사항", color = PetCheckerColorPalette.current.txtColor)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MoreViewPreview(){
    MoreView()
}