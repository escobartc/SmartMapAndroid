package com.example.smartmap.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {

    private val _user = mutableStateOf("")
    val usuario = _user
    private val _password = mutableStateOf("")
    val password = _password
    private val _notValidUser = mutableStateOf(true)
    private val _notValidPassword = mutableStateOf(true)
    fun validateLoginFields() = (_notValidUser.value == false && _notValidPassword.value == false)


    fun onUserTextChange(texto: String) {
        _user.value = texto
        validateMail()
    }

    fun onPasswordTextChange(texto: String) {
        _password.value = texto
        validatePassword()
    }

    fun validatePassword() {
        _notValidPassword.value = _password.value.isEmpty()
    }

    private fun validateMail() {
        _notValidUser.value = !_user.value.contains("@javeriana.edu.co")
    }

}