package com.cj.petchecker.userManagement.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.rounded.AlternateEmail
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cj.petchecker.ui.theme.PetCheckerColorPalette
import com.cj.petchecker.ui.theme.PetCheckerTheme
import com.cj.petchecker.ui.theme.accent
import com.cj.petchecker.ui.theme.getTxtFieldColors
import com.cj.petchecker.ui.theme.gray
import com.cj.petchecker.userManagement.helper.UserManagement

fun getResetPasswordAlertContents(code: Int): Pair<String, String>{
    return when(code){
        0 -> "공백 필드" to "모든 필드를 채워주세요."
        1 -> "잘못된 형식의 E-Mail" to "올바른 형식의 E-Mail을 입력해주세요."
        2 -> "작업 완료" to "비밀번호 재설정 메일이 발송되었습니다."
        3 -> "오류" to "요청하신 작업을 처리하는 중 문제가 발생했습니다.\n가입한 E-Mail이 아니거나, 네트워크 상태를 확인한 후 다시 시도하세요."
        else -> "" to ""
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordView(navController: NavController){
    val email = remember {
        mutableStateOf("")
    }
    
    val showProgress = remember {
        mutableStateOf(false)
    }

    val showAlert = remember {
        mutableStateOf(false)
    }

    val alertCode = remember{
        mutableIntStateOf(-1)
    }

    val alertTitle = remember{
        mutableStateOf("")
    }

    val alertMessage = remember {
        mutableStateOf("")
    }

    val helper = UserManagement()

    PetCheckerTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("비밀번호 재설정") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = PetCheckerColorPalette.current.background,
                        titleContentColor = accent
                    ),
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.navigateUp()
                        }) {
                            Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null, tint = accent)
                        }
                    })
            }
        ) {
            Surface(modifier = Modifier
                .fillMaxSize()
                .padding(it), color = PetCheckerColorPalette.current.background) {
                Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(imageVector = Icons.Rounded.AlternateEmail, contentDescription = null, tint = accent, modifier = Modifier.size(80.dp))
                    Text(text = "도움이 필요하신가요?", color = accent, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = "아래 필드에 가입한 E-Mail을 입력하시면 비밀번호 재설정 메일이 발송됩니다.", color = gray, fontSize = 12.sp)

                    Spacer(modifier = Modifier.weight(1f))

                    TextField(value = email.value, onValueChange = { email.value = it }, label = {
                        Text(text = "E-Mail")
                    }, leadingIcon = {
                        Icon(imageVector = Icons.Rounded.AlternateEmail, contentDescription = null)
                    }, modifier = Modifier.fillMaxWidth(), colors = getTxtFieldColors(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Button(onClick = {
                        if(email.value == ""){
                            alertCode.intValue = 0
                        } else if(!email.value.contains("@")){
                            alertCode.intValue = 1
                        }

                        if(alertCode.intValue != -1){
                            val (title, message) = getResetPasswordAlertContents(alertCode.intValue)
                            alertTitle.value = title
                            alertMessage.value = message

                            showAlert.value = true
                        } else{
                            showProgress.value = true

                            helper.sendResetPasswordMail(email.value){
                                alertCode.intValue = if(it) 2 else 3

                                val (title, message) = getResetPasswordAlertContents(alertCode.intValue)
                                alertTitle.value = title
                                alertMessage.value = message

                                showProgress.value = false

                                showAlert.value = true
                            }
                        }
                    }, colors = ButtonDefaults.buttonColors(accent), elevation = ButtonDefaults.buttonElevation(15.dp)) {
                        Text(text = "비밀번호 재설정")
                    }

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
                                Text(alertTitle.value)
                            },
                            text = {
                                Text(alertMessage.value)
                            },
                            icon = {
                                Icon(imageVector = if(alertCode.intValue == 2) Icons.Rounded.CheckCircle else Icons.Rounded.Cancel, contentDescription = null)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ResetPasswordViewPreview(){
    ResetPasswordView(rememberNavController())
}