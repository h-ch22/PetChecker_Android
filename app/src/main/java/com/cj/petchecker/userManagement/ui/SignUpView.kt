package com.cj.petchecker.userManagement.ui

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.rounded.AlternateEmail
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.Key
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cj.petchecker.frameworks.ui.MainActivity
import com.cj.petchecker.ui.theme.PetCheckerColorPalette
import com.cj.petchecker.ui.theme.PetCheckerTheme
import com.cj.petchecker.ui.theme.accent
import com.cj.petchecker.ui.theme.getTxtFieldColors
import com.cj.petchecker.userManagement.helper.UserManagement

fun getSignUpAlertContents(code: Int): Pair<String, String>{
    return when(code){
        0 -> "공백 필드" to "모든 필드를 채워주세요."
        1 -> "올바르지 않은 형식의 E-Mail" to "올바른 형식의 E-Mail을 입력해주세요."
        2 -> "안전하지 않은 비밀번호" to "보안을 위해 6자리 이상의 비밀번호를 입력해주세요."
        3 -> "비밀번호 불일치" to "비밀번호와 비밀번호 확인이 일치하지 않습니다."
        4 -> "이용 약관 동의" to "필수 이용 약관을 읽고 동의해주세요."
        5 -> "오류" to "요청하신 작업을 처리하는 중 문제가 발생했습니다.\n네트워크 상태를 확인하거나 이미 가입된 E-Mail인지 확인한 후 다시 시도하십시오."
        else -> "" to ""
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpView(navController: NavController){
    val email = remember {
        mutableStateOf("")
    }

    val password = remember{
        mutableStateOf("")
    }

    val checkPassword = remember{
        mutableStateOf("")
    }

    val name = remember{
        mutableStateOf("")
    }

    val phone = remember{
        mutableStateOf("")
    }

    val isAgreeEULA = remember{
        mutableStateOf(false)
    }

    val isAgreePrivacy = remember{
        mutableStateOf(false)
    }

    val alertCode = remember{
        mutableIntStateOf(-1)
    }

    val alertTitle = remember{
        mutableStateOf("")
    }

    val alertMessage = remember{
        mutableStateOf("")
    }

    val showAlert = remember{
        mutableStateOf(false)
    }
    
    val showProgress = remember{
        mutableStateOf(false)
    }

    val helper = UserManagement()
    val context = LocalContext.current

    PetCheckerTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("회원가입") },
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
                Column(modifier = Modifier
                    .padding(20.dp)
                    .scrollable(rememberScrollState(), Orientation.Vertical)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        Text(text = "반가워요!", color = PetCheckerColorPalette.current.txtColor, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(value = email.value, onValueChange = { email.value = it }, label = {
                        Text(text = "E-Mail")
                    }, leadingIcon = {
                        Icon(imageVector = Icons.Rounded.AlternateEmail, contentDescription = null)
                    }, modifier = Modifier.fillMaxWidth(), colors = getTxtFieldColors(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )

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

                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        value = checkPassword.value,
                        onValueChange = { checkPassword.value = it },
                        label = { Text(text = "비밀번호 확인") },
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

                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        value = name.value,
                        onValueChange = { name.value = it },
                        label = { Text(text = "이름") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Person,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = getTxtFieldColors()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        value = phone.value,
                        onValueChange = { phone.value = it },
                        label = { Text(text = "연락처") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Phone,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = getTxtFieldColors()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
                        Checkbox(checked = isAgreeEULA.value, onCheckedChange = {
                            isAgreeEULA.value = it
                        }, colors = CheckboxDefaults.colors(accent))

                        Text(text = "최종 사용권 계약서", color = PetCheckerColorPalette.current.txtColor)

                        Spacer(modifier = Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
                        Checkbox(checked = isAgreePrivacy.value, onCheckedChange = {
                            isAgreePrivacy.value = it
                        }, colors = CheckboxDefaults.colors(accent))

                        Text(text = "개인정보 처리 방침", color = PetCheckerColorPalette.current.txtColor)

                        Spacer(modifier = Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(onClick = {
                        if(email.value == "" || password.value == "" || checkPassword.value == "" || name.value == "" || phone.value == ""){
                            alertCode.intValue = 0
                        } else if(!email.value.contains("@")){
                            alertCode.intValue = 1
                        } else if(password.value.length < 6){
                            alertCode.intValue = 2
                        } else if(password.value != checkPassword.value){
                            alertCode.intValue = 3
                        } else if(!isAgreeEULA.value || !isAgreePrivacy.value){
                            alertCode.intValue = 4
                        } else{
                            alertCode.intValue = -1
                        }

                        if(alertCode.intValue != -1){
                            val (title, message) = getSignUpAlertContents(alertCode.intValue)
                            alertTitle.value = title
                            alertMessage.value = message
                            showAlert.value = true
                        } else{
                            showProgress.value = true

                            helper.signUp(email = email.value, password = password.value, name = name.value, phone = phone.value){
                                if(!it){
                                    alertCode.intValue = 5

                                    val (title, message) = getSignUpAlertContents(alertCode.intValue)
                                    alertTitle.value = title
                                    alertMessage.value = message
                                    showProgress.value = false
                                    showAlert.value = true
                                } else{
                                    showProgress.value = false
                                    context.startActivity(Intent(context, MainActivity :: class.java))
                                }
                            }
                        }
                    }, colors = ButtonDefaults.buttonColors(accent), elevation = ButtonDefaults.buttonElevation(15.dp)) {
                        Text(text = "회원가입")
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
                                Icon(imageVector = Icons.Rounded.Cancel, contentDescription = null)
                            }
                        )
                    }

                }
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun SignUpViewPreview(){
    SignUpView(navController = rememberNavController())
}