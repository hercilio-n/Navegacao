package com.senac.navegacao.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel : ViewModel() {
    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn: StateFlow<Boolean?> = _isLoggedIn.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val registeredUsers = mutableMapOf<String, String>()

    fun authenticate(username: String, password: String) {
        if (registeredUsers.containsKey(username) && registeredUsers[username] == password) {
            _isLoggedIn.value = true
            _errorMessage.value = null
        } else {
            _isLoggedIn.value = false
            _errorMessage.value = "Usuário/Senha inválidos"
        }
    }

    fun register(username: String, password: String, email: String) {
        if (username.isNotBlank() && password.isNotBlank() && email.isNotBlank() && !registeredUsers.containsKey(username)) {
            registeredUsers[username] = password
            _errorMessage.value = null
        } else {
            _errorMessage.value = "Erro ao registrar usuário"
        }
    }

    fun getCurrentUsername(): String {
        return "Usuário"
    }
}