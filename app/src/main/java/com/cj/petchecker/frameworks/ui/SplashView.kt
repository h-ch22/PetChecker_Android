package com.cj.petchecker.frameworks.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cj.petchecker.ui.theme.PetCheckerColorPalette
import com.cj.petchecker.ui.theme.PetCheckerTheme
import com.cj.petchecker.ui.theme.accent
import com.cj.petchecker.userManagement.helper.UserManagement
import com.cj.petchecker.userManagement.ui.SignInView

@Composable
fun SplashView(){
    val navController = rememberNavController()

    PetCheckerTheme {
        LaunchedEffect(key1 = null){
            if(UserManagement.getUserInfo() == null){
                navController.navigate("SignInView"){
                    popUpTo("SplashView"){
                        inclusive = true
                    }
                }
            }
        }

        NavHost(navController = navController, startDestination = "SplashView"){
            composable("SplashView"){
                Surface(modifier = Modifier.fillMaxSize(), color = PetCheckerColorPalette.current.background) {
                    Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.weight(1f))

                        ImageLogo(imageSize = 200.dp)

                        Spacer(modifier = Modifier.height(10.dp))

                        TextLogo(fontSize = 25.sp)

                        Spacer(modifier = Modifier.weight(1f))

                        CircularProgressIndicator(color = accent)
                    }
                }
            }

            composable("SignInView"){
                SignInView()
            }
        }
    }
}