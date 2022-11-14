package com.example.smartmap.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.smartmap.R
import com.example.smartmap.model.EdificioApp
import com.example.smartmap.ui.*
import com.example.smartmap.viewModel.AuthViewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_ZOOM
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {
    val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        EdificioApp.applicationContext = applicationContext
        if (FirebaseAuth.getInstance().currentUser?.uid != null
        ) {
            val intend = Intent(this, MapActivity::class.java)
            startActivity(intend)
        } else {
            findViewById<ComposeView>(R.id.composeView).setContent {
                Scaffold() {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = colorResource(id = R.color.blue)
                    ) {
                        Login(videoUri = getVideoUri())
                    }
                }
            }
        }
    }

    @Composable
    fun Login(videoUri: Uri) {
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
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Bottom) {
                AppTitle()
                Spacer(modifier = Modifier.size(20.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AppTextField(
                        label = stringResource(id = R.string.correo),
                        value = viewModel.usuario.value,
                        hide = false,
                        onTextChange = { viewModel.onUserTextChange(it) },
                        Icon = { Mail() })
                    Spacer(modifier = Modifier.size(20.dp))
                    AppTextField(
                        label = stringResource(id = R.string.contraseña),
                        value = viewModel.password.value,
                        hide = true,
                        onTextChange = { viewModel.onPasswordTextChange(it) },
                        Icon = { Password() }
                    )
                    Spacer(modifier = Modifier.size(20.dp))
                    Row {
                        Button(
                            modifier = Modifier
                                .width(width = 154.dp)
                                .height(50.dp)
                                .border(
                                    width = 1.dp,
                                    shape = RoundedCornerShape(10.dp),
                                    color = Color.Black
                                ),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = colorResource(
                                    id = R.color.yellow
                                )
                            ),
                            onClick = { nextActivity() }) {
                            Text(
                                text = "Ingresar",
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.size(12.dp))
                        Button(
                            modifier = Modifier
                                .width(width = 154.dp)
                                .height(50.dp)
                                .border(
                                    width = 1.dp,
                                    shape = RoundedCornerShape(10.dp),
                                    color = Color.Black
                                ),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = colorResource(
                                    id = R.color.yellow
                                )
                            ),
                            onClick = { registerActivity() }) {
                            Text(
                                text = "Registrarse",
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        }
                    }
                    Spacer(modifier = Modifier.size(40.dp))
                    Divider(
                        modifier = Modifier.width(320.dp),
                        color = Color.White,
                        thickness = 2.dp
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    LinkButton(text = "Ingresar como invitado") {
                        nextActivityAsGuest()
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                }
            }
        }
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
            layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            useController = false
            resizeMode = RESIZE_MODE_ZOOM
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