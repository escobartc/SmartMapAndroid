package com.example.smartmap.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartmap.R
import com.example.smartmap.model.LugarApp
import com.example.smartmap.ui.AppTextField
import com.example.smartmap.ui.AppTitle
import com.example.smartmap.ui.LinkButton
import com.example.smartmap.viewModel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {
    val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        LugarApp.applicationContext = applicationContext
        findViewById<ComposeView>(R.id.composeView).setContent {
            Scaffold() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.blue)
                ) {
                    Column() {
                        AppTitle()
                        Spacer(modifier = Modifier.size(60.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AppTextField(
                                label = stringResource(id = R.string.correo),
                                value = viewModel.usuario.value,
                                hide = false,
                                onTextChange = { viewModel.onUserTextChange(it) })
                            Spacer(modifier = Modifier.size(20.dp))
                            AppTextField(
                                label = stringResource(id = R.string.contraseña),
                                value = viewModel.password.value,
                                hide = true,
                                onTextChange = { viewModel.onPasswordTextChange(it) })
                            Row(Modifier.padding(top = 40.dp, bottom = 20.dp)) {
                                Button(
                                    modifier = Modifier
                                        .size(width = 139.dp, height = 80.dp)
                                        .border(
                                            width = 1.dp,
                                            shape = RoundedCornerShape(12.dp),
                                            color = Color.Black
                                        ),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = colorResource(
                                            id = R.color.yellow
                                        )
                                    ),
                                    onClick = { nextActivity() }) {
                                    Text(
                                        text = "Ingresar",
                                        fontSize = 18.sp,
                                        color = Color.Black
                                    )
                                }
                                Spacer(modifier = Modifier.size(12.dp))
                                Button(
                                    modifier = Modifier
                                        .size(width = 139.dp, height = 80.dp)
                                        .border(
                                            width = 1.dp,
                                            shape = RoundedCornerShape(12.dp),
                                            color = Color.Black
                                        ),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = colorResource(
                                            id = R.color.yellow
                                        )
                                    ),
                                    onClick = { registerActivity() }) {
                                    Text(
                                        text = "Registrarse",
                                        fontSize = 18.sp,
                                        color = Color.Black
                                    )
                                }
                            }
                            LinkButton(text = "Ingresar como invitado") {
                                nextActivityAsGuest()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun nextActivity() {
        val firebaseAuth = FirebaseAuth.getInstance()

        if (viewModel.validateLoginFields()) {
            firebaseAuth
                .signInWithEmailAndPassword(
                    viewModel.usuario.value,
                    viewModel.password.value
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val user = firebaseAuth.currentUser
                        if (user != null) {
                            if (user.isEmailVerified) {
                                val intend = Intent(this, MapActivity::class.java)
                                startActivity(intend)
                            } else {
                                showAlert("El usuario no ha verificado su correo")
                            }
                        } else {
                            showAlert("Se produjo un error en el login")
                        }
                    } else {
                        showAlert("Se produjo un error en el login")
                    }
                }
        } else {
            showAlert("Datos incorrectos o incompletos")
        }
    }

    private fun nextActivityAsGuest() {
        val intend = Intent(this, MapActivity::class.java)
        startActivity(intend)
    }

    private fun registerActivity() {
        val intend = Intent(this, RegisterActivity::class.java)
        startActivity(intend)
    }

    private fun showAlert(string: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Alerta")
        builder.setMessage(string)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}