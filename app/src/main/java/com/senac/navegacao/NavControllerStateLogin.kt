package com.senac.navegacao

import androidx.compose.runtime.*
import androidx.navigation.NavController
class NavControllerStateLogin(navController: NavController) {
    var username by mutableStateOf("")
    var password by mutableStateOf("")
}
fun NavController.loginState(): NavControllerStateLogin {
    return NavControllerStateLogin(this)
}