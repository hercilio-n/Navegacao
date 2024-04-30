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
        // Adicione aqui os itens do menu

        // Botão para voltar para a tela de login
        Button(onClick = { navController.navigate("login") }) {
            Text(text = "Logout")
        }
    }
}