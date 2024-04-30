package com.senac.navegacao.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.senac.navegacao.loginState

@Composable
fun LoginScreen(navController: NavController) {
    val navControllerStateLogin= remember(navController) { navController.loginState() }
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Login", style = MaterialTheme.typography.headlineMedium)
        OutlinedTextField(
            value = navControllerStateLogin.username,
            onValueChange = { navControllerStateLogin.username = it },
            label = { Text("Username") }
        )
        OutlinedTextField(
            value = navControllerStateLogin.password,
            onValueChange = { navControllerStateLogin.password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Button(onClick = { navController.navigate("userActivity/${navControllerStateLogin.username}") }) {
            Text("Login")
        }
        Button(onClick = { navController.navigate("register") }) {
            Text("Register")
        }
    }
}