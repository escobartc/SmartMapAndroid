package com.example.smartmap.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartmap.R
import com.example.smartmap.ui.AppTextField
import com.example.smartmap.ui.AppTitle
import com.example.smartmap.viewModel.RegisterViewModel
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        findViewById<ComposeView>(R.id.composeViewRegistro).setContent {
            Scaffold() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.blue)
                ) {
                    Column() {
                        Row() {
                            Icon(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clickable {
                                        nextActivity()
                                    },
                                painter = painterResource(id = androidx.appcompat.R.drawable.abc_ic_ab_back_material),
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                        AppTitle()
                        Spacer(modifier = Modifier.size(40.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AppTextField(
                                label = stringResource(id = R.string.nombre),
                                value = viewModel.name.value,
                                hide = false,
                                onTextChange = { viewModel.onNameTextChange(it) })
                            Spacer(modifier = Modifier.size(20.dp))
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
                                Spacer(modifier = Modifier.size(12.dp))
                                Button(
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .height(66.dp)
                                        .border(
                                            width = 1.dp,
                                            shape = RoundedCornerShape(12.dp),
                                            color = Color.Black
                                        ),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = if (viewModel.validateFields()) {
                                        ButtonDefaults.buttonColors(
                                            backgroundColor = colorResource(
                                                id = R.color.yellow
                                            )
                                        )
                                    } else {
                                        ButtonDefaults.buttonColors(
                                            backgroundColor = colorResource(
                                                id = R.color.gris
                                            )
                                        )
                                    },
                                    enabled = viewModel.validateFields(),
                                    onClick = {
                                        val firebaseAuth = FirebaseAuth.getInstance()
                                        if (viewModel.validateFields()) {
                                            firebaseAuth
                                                .createUserWithEmailAndPassword(
                                                    viewModel.usuario.value,
                                                    viewModel.password.value
                                                ).addOnCompleteListener {
                                                    if (it.isSuccessful) {
                                                        firebaseAuth
                                                            .signInWithEmailAndPassword(
                                                                viewModel.usuario.value,
                                                                viewModel.password.value
                                                            ).addOnCompleteListener { login ->
                                                                if (login.isSuccessful) {
                                                                    verification()
                                                                } else {
                                                                    showAlert("No se ha podido enviar el correo de verificación ")
                                                                }
                                                            }
                                                    } else {
                                                        showAlert("Se produjo un error en la creación del usuario")
                                                    }
                                                }
                                        } else {
                                            showAlert("Por favor, llene correctamente los campos")

                                        }

                                    }) {
                                    Text(
                                        text = "Registrarse",
                                        fontSize = 24.sp,
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showAlert(string: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Alerta")
        builder.setMessage(string)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun nextActivity() {
        val intend = Intent(this, AuthActivity::class.java)
        startActivity(intend)
    }

    private fun verification() {
        //get instance of firebase auth
        val firebaseAuth = FirebaseAuth.getInstance()
        //get current user
        val firebaseUser = firebaseAuth.currentUser

        //send email verification
        firebaseUser!!.sendEmailVerification()
            .addOnSuccessListener {
                showAlert("Se envió un correo de verificación")
                firebaseAuth.signOut()
            }
            .addOnFailureListener { e ->
                showAlert("No se ha podido enviar el correo de verificación debido a ${e.message}")
            }
    }
}

