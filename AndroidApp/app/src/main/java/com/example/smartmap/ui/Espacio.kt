package com.example.smartmap.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartmap.R

@Composable
fun MostrarEspacio(
    nombre: String,
    sePuedeComer: Boolean,
    tipoDeEspacio: String,
    prestamoDeEquipos: Boolean,
    piso: String
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight()
            .padding(12.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, Color.Black)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(0.8f).padding(6.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = nombre,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Visible,
                    maxLines = 2,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                )
            }
            Spacer(modifier = Modifier.size(4.dp))
            Row(Modifier.fillMaxSize()) {
                Column(Modifier.wrapContentSize()) {
                    Text(
                        modifier = Modifier.padding(6.dp),
                        text = "Tipo de espacio: $tipoDeEspacio",
                        fontSize = 14.sp,
                        overflow = TextOverflow.Visible,
                        maxLines = 3,
                        textAlign = TextAlign.Justify,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif
                    )
                    Text(
                        modifier = Modifier.padding(6.dp),
                        text = "Se encuentra en el piso $piso",
                        fontSize = 14.sp,
                        overflow = TextOverflow.Visible,
                        maxLines = 3,
                        textAlign = TextAlign.Justify,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif
                    )
                    Text(
                        modifier = Modifier.padding(6.dp),
                        text = "Prestamo de equipos:" + if (prestamoDeEquipos) {
                            " Si"
                        } else {
                            " No"
                        },
                        fontSize = 14.sp,
                        overflow = TextOverflow.Visible,
                        maxLines = 3,
                        textAlign = TextAlign.Justify,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif
                    )
                    Text(
                        modifier = Modifier.padding(6.dp),
                        text = "¿Se puede comer?:" + if (sePuedeComer) {
                            " Si"
                        } else {
                            " No"
                        },
                        fontSize = 14.sp,
                        overflow = TextOverflow.Visible,
                        maxLines = 50,
                        textAlign = TextAlign.Justify,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif
                    )
                }
                Spacer(modifier = Modifier.size(4.dp))

                Column(
                    modifier = Modifier
                        .fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally
                ){
                    Box {
                        Image(modifier = Modifier.fillMaxSize(0.8f).border(2.dp, color = Color.Black).padding(6.dp), alignment = Alignment.Center, painter = painterResource(id = R.drawable.space), contentDescription = "espacio")

                    }
                }
            }
        }
    }
}