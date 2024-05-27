package com.senac.navegacao.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun MenuScreen(navController: NavController, username: String) {
    Column {
        Text(text = "Bem-Vindo, $username!")
        Button(onClick = { navController.navigate("login") }) {
            Text(text = "Logout")
        }
    }
}