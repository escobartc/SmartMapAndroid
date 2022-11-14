package com.example.smartmap.view

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.smartmap.R
import com.example.smartmap.ui.*
import com.example.smartmap.viewModel.RegisterViewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.ktx.Firebase

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
                    Register(videoUri = getVideoUri())
                }
            }
        }
    }

    @Composable
    fun Register(videoUri: Uri) {
        val context = LocalContext.current
        val exoPlayer = remember { context.buildExoPlayer(videoUri) }

        DisposableEffect(
            AndroidView(
                factory = { it.buildPlayerView(exoPlayer) },
                modifier = Modifier.fillMaxSize()
            )
        ) {
            onDispose {
                exoPlayer.release()
            }
        }

        ProvideWindowInsets {
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
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppTitle()
                Spacer(modifier = Modifier.size(40.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    AppTextField(
                        label = stringResource(id = R.string.nombre),
                        value = viewModel.name.value,
                        hide = false,
                        onTextChange = { viewModel.onNameTextChange(it) },
                        Icon = { Nombre() }
                    )
                    Spacer(modifier = Modifier.size(20.dp))
                    AppTextField(
                        label = stringResource(id = R.string.correo),
                        value = viewModel.usuario.value,
                        hide = false,
                        onTextChange = { viewModel.onUserTextChange(it) },
                        Icon = { Mail() }
                    )
                    Spacer(modifier = Modifier.size(20.dp))
                    AppTextField(
                        label = stringResource(id = R.string.contraseña),
                        value = viewModel.password.value,
                        hide = true,
                        onTextChange = { viewModel.onPasswordTextChange(it) },
                        Icon = { Password() }
                    )
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
                                terminosDeUso()
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


    private fun registrarse() {
        val firebaseAuth = FirebaseAuth.getInstance()
        if (viewModel.validateFields()) {
            firebaseAuth
                .createUserWithEmailAndPassword(
                    viewModel.usuario.value,
                    viewModel.password.value
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(viewModel.name.value)
                            .build()
                        firebaseAuth.currentUser?.updateProfile(profileUpdates)
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

    }

    private fun terminosDeUso() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Alerta")
        builder.setMessage(R.string.terminos)
        builder.setPositiveButton("Si") { dialog, which ->
            registrarse()
        }
        builder.setNegativeButton("No") { dialog, which ->

        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    internal fun getVideoUri(): Uri {
        val rawId = resources.getIdentifier("javeriana", "raw", packageName)
        val videoUri = "android.resource://$packageName/$rawId"
        return Uri.parse(videoUri)
    }

    internal fun Context.buildExoPlayer(uri: Uri) =
        ExoPlayer.Builder(this).build().apply {
            setMediaItem(MediaItem.fromUri(uri))
            repeatMode = Player.REPEAT_MODE_ALL
            playWhenReady = true
            prepare()
        }

    internal fun Context.buildPlayerView(exoPlayer: ExoPlayer) =
        StyledPlayerView(this).apply {
            player = exoPlayer
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            useController = false
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
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
        finish()
    }

    private fun verification() {
        //get instance of firebase auth
        val firebaseAuth = FirebaseAuth.getInstance()
        //get current user
        val firebaseUser = firebaseAuth.currentUser

        //send email verification
        firebaseUser!!.sendEmailVerification()
            .addOnSuccessListener {
                viewModel.enviarUsuario()
                showAlert("Se envió un correo de verificación a ${viewModel.usuario.value}, por favor revisar en spam.")
                firebaseAuth.signOut()
            }
            .addOnFailureListener { e ->
                showAlert("No se ha podido enviar el correo de verificación debido a ${e.message}")
            }
    }
}

