package com.example.smartmap.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    private val _user = mutableStateOf("")
    val usuario = _user
    private val _name = mutableStateOf("")
    val name = _name
    private val _password = mutableStateOf("")
    val password = _password
    private val _notValidUser = mutableStateOf(true)
    private val _notValidPassword = mutableStateOf(true)
    private val _notValidName = mutableStateOf(true)
    fun validateFields() = (_notValidUser.value == false && _notValidPassword.value == false && _notValidName.value == false)


    fun onUserTextChange(texto: String) {
        _user.value = texto
        validateMail()
    }

    fun onPasswordTextChange(texto: String) {
        _password.value = texto
        validatePassword()
    }

    fun onNameTextChange(texto: String) {
        _name.value = texto
        validateName()
    }

    private fun validateName() {
        _notValidName.value = _name.value.isEmpty()
    }

    fun validatePassword() {
        _notValidPassword.value = _password.value.isEmpty()
    }

    private fun validateMail() {
        _notValidUser.value = !_user.value.contains("@javeriana.edu.co")
    }

}