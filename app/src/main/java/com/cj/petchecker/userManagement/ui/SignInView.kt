package com.cj.petchecker.userManagement.ui

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.rounded.AlternateEmail
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Key
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cj.petchecker.frameworks.ui.ImageLogo
import com.cj.petchecker.frameworks.ui.MainActivity
import com.cj.petchecker.frameworks.ui.TextLogo
import com.cj.petchecker.ui.theme.PetCheckerColorPalette
import com.cj.petchecker.ui.theme.PetCheckerTheme
import com.cj.petchecker.ui.theme.accent
import com.cj.petchecker.ui.theme.getTxtFieldColors
import com.cj.petchecker.ui.theme.white
import com.cj.petchecker.userManagement.helper.UserManagement

fun getAlertTitle(code: Int): String{
    return when(code){
        0 -> "공백 필드"
        1 -> "오류"
        else -> ""
    }
}

fun getAlertMessage(code: Int): String{
    return when(code){
        0 -> "모든 필드를 채워주세요."
        1 -> "요청하신 작업을 처리하는 중 문제가 발생했습니다.\n네트워크 상태를 확인하거나 사용자 정보를 확인한 후 다시 시도하십시오."
        else -> ""
    }
}

@Composable
fun SignInView() {
    val email = remember {
        mutableStateOf("")
    }

    val password = remember {
        mutableStateOf("")
    }

    val showAlert = remember{
        mutableStateOf(false)
    }
    
    val showProgress = remember{
        mutableStateOf(false)
    }

    val alertCode = remember{
        mutableIntStateOf(-1)
    }

    val navController = rememberNavController()
    val helper = UserManagement()
    val context = LocalContext.current

    PetCheckerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = PetCheckerColorPalette.current.background
        ) {
            NavHost(navController = navController, startDestination = "SignInView"){
                composable("SignInView"){
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.weight(1f))

                        ImageLogo(imageSize = 150.dp)

                        Spacer(modifier = Modifier.height(10.dp))

                        TextLogo()

                        Spacer(modifier = Modifier.weight(1f))

                        TextField(value = email.value, onValueChange = { email.value = it }, label = {
                            Text(text = "E-Mail")
                        }, leadingIcon = {
                            Icon(imageVector = Icons.Rounded.AlternateEmail, contentDescription = null)
                        }, modifier = Modifier.fillMaxWidth(), colors = getTxtFieldColors(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email))

                        Spacer(modifier = Modifier.height(10.dp))

                        TextField(
                            value = password.value,
                            onValueChange = { password.value = it },
                            label = { Text(text = "비밀번호") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Rounded.Key,
                                    contentDescription = null
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation = PasswordVisualTransformation(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            colors = getTxtFieldColors()
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Button(
                            onClick = {
                                if(email.value == "" || password.value == ""){
                                    alertCode.intValue = 0
                                    showAlert.value = true
                                } else{
                                    showProgress.value = true

                                    helper.signIn(email.value, password.value){
                                        if(it){
                                            context.startActivity(Intent(context, MainActivity :: class.java))
                                        } else{
                                            alertCode.intValue = 1
                                            showAlert.value = true
                                        }

                                        showProgress.value = false
                                    }
                                }
                            },
                            modifier = Modifier
                                .width(250.dp)
                                .height(60.dp),
                            colors = ButtonDefaults.buttonColors(accent),
                            elevation = ButtonDefaults.buttonElevation(5.dp)
                        ) {
                            Text(text = "로그인", color = white)
                            Icon(
                                imageVector = Icons.Rounded.ChevronRight,
                                contentDescription = null,
                                tint = white
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            TextButton(onClick = {
                                navController.navigate("ResetPasswordView"){
                                    popUpTo("SignInView"){
                                        inclusive = false
                                    }
                                }
                            }) {
                                Text(text = "비밀번호를 잊으셨나요?", color = accent)
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            TextButton(onClick = {
                                navController.navigate("SignUpView"){
                                    popUpTo("SignInView"){
                                        inclusive = false
                                    }
                                }
                            }) {
                                Text(text = "회원가입", color = accent)
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        if(showProgress.value){
                            Dialog(
                                onDismissRequest = { showProgress.value = false },
                                DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
                            ) {
                                Box(
                                    contentAlignment= Alignment.Center,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .background(
                                            PetCheckerColorPalette.current.background.copy(alpha = 0.7f),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                ) {
                                    CircularProgressIndicator(color = accent)
                                }
                            }
                        }

                        if(showAlert.value){
                            AlertDialog(
                                onDismissRequest = { showAlert.value = false },

                                confirmButton = {
                                    TextButton(onClick = {
                                        showAlert.value = false
                                    }){
                                        Text("확인", color = accent, fontWeight = FontWeight.Bold)
                                    }
                                },
                                title = {
                                    Text(getAlertTitle(alertCode.intValue))
                                },
                                text = {
                                    Text(getAlertMessage(alertCode.intValue))
                                },
                                icon = {
                                    Icon(imageVector = Icons.Rounded.Cancel, contentDescription = null)
                                }
                            )
                        }
                    }
                }

                composable("SignUpView"){
                    SignUpView(navController)
                }

                composable("ResetPasswordView"){
                    ResetPasswordView(navController = navController)
                }
            }
        }
    }
}

@Preview
@Composable
fun SignInViewPreview() {
    SignInView()
}