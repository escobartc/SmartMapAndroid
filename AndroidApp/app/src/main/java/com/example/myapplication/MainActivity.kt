package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.AppTextField
import com.example.myapplication.ui.AppTitle
import com.example.myapplication.ui.LinkButton
import com.unity3d.player.UnityPlayerActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<ComposeView>(R.id.composeView).setContent {
            Scaffold() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Blue
                ) {
                    Column() {
                        AppTitle()
                        Spacer(modifier = Modifier.size(60.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AppTextField("User", "carlos")
                            Spacer(modifier = Modifier.size(20.dp))
                            AppTextField("Password", "******")
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
                                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow),
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
                                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow),
                                    onClick = { /*TODO*/ }) {
                                    Text(
                                        text = "Registrarse",
                                        fontSize = 18.sp,
                                        color = Color.Black
                                    )
                                }
                            }
                            LinkButton(text = "Ingresar como invitado") {
                                nextActivity()
                            }
                        }
                    }
                }
                // A surface container using the 'background' color from the theme
            }
        }
    }

    private fun nextActivity() {
        val intend = Intent(this, UnityPlayerActivity::class.java)
        startActivity(intend)
    }
}