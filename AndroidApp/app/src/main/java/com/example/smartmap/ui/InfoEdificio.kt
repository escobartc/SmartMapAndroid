package com.example.smartmap.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily.Companion.SansSerif
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.smartmap.R
import com.example.smartmap.model.Espacio

@Preview
@Composable
private fun Preview() {
    InfoEdificio(
        numero = "12",
        nombre = "EDIFICIO JOSÉ GABRIEL MALDONADO S.J.",
        descripcion = "La Facultad de Ingeniería cuenta con un moderno edificio de laboratorios de 14.000 metros cuadrados destinados a la docencia, investigación y servicio que impulsan el crecimiento y desarrollo tecnológico del país.\n" +
                "\n" +
                "Inaugurado en enero del año 2020, esta imponente estructura de color dorado, inspirado en la frase del himno de la Universidad “El Bronce de los Siglos”, cuenta con más de 96 espacios donde se busca que nuestros estudiantes e investigadores desarrollen proyectos colaborativos con impacto social, excelencia y responsabilidad; y responder a los servicios del sector productivo donde la Facultad de Ingeniería sea un socio estratégico de la industria.\n" +
                "\n" +
                "Cada uno de los espacios del edificio fue diseñado para ser un espacio vivo de aprendizaje bajo la premisa de compartir el conocimiento, respondiendo a líneas de investigación como big data y analytics, inteligencia artificial, internet de las cosas, energía y biorecursos, salud, infraestructura sostenible y transporte, ambiente y cambio climático, TIC y sociedad, organización y productividad corporativa, materiales de construcción entre otras.\n" +
                "\n" +
                "Como una muestra más del compromiso de la Pontificia Universidad Javeriana con el cuidado de nuestra casa común, el edificio fue reconocido con la Certificación Edge del Banco Mundial, donde se resaltan sus características de uso eficiente de agua y energía, uso de materiales amigables con el medio ambiente y su diseño bioclimático.",
        onClick = {},
        imagen = "https://www.example.com/image.jpg",
        esInvitado = false,
        espacios = emptyList(),
        onClickAforo = {}
    )

}

@Composable
fun InfoEdificio(
    numero: String,
    nombre: String,
    descripcion: String,
    onClick: () -> Unit,
    imagen: String,
    esInvitado: Boolean,
    espacios: List<Espacio> = emptyList(),
    onClickAforo: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize(), color = colorResource(id = R.color.blue)) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 32.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp),
                        text = "$numero $nombre",
                        fontSize = 22.sp,
                        overflow = TextOverflow.Visible,
                        maxLines = 2,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontFamily = SansSerif
                    )
                }
                Row(modifier = Modifier.size(220.dp)) {
                    SubcomposeAsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = imagen.ifEmpty {
                            "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6c/Javeriana.svg/1200px-Javeriana.svg.png"
                        },
                        loading = {
                            CircularProgressIndicator(
                                color = colorResource(id = R.color.blue)
                            )
                        },
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.size(4.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = descripcion,
                            fontSize = 18.sp,
                            overflow = TextOverflow.Visible,
                            maxLines = 50,
                            textAlign = TextAlign.Justify,
                            fontWeight = FontWeight.Bold,
                            fontFamily = SansSerif
                        )
                    }
                    if (!esInvitado && espacios.isNotEmpty()) {
                        item {
                            Text(
                                modifier = Modifier.padding(vertical = 4.dp),
                                text = "Espacios",
                                fontSize = 22.sp,
                                overflow = TextOverflow.Visible,
                                maxLines = 2,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontFamily = SansSerif
                            )
                        }

                        items(1) {
                            espacios.forEach {
                                MostrarEspacio(
                                    nombre = it.nombre,
                                    piso = it.piso,
                                    prestamoDeEquipos = it.prestamoEquipos,
                                    sePuedeComer = it.sePuedeComer,
                                    tipoDeEspacio = it.tipoEspacio
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.size(8.dp))
                Row {
                    Button(
                        modifier = Modifier
                            .size(width = 139.dp, height = 60.dp)
                            .border(
                                width = 1.dp,
                                shape = RoundedCornerShape(12.dp),
                                color = Color.Black
                            ),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.yellow)),
                        onClick = { onClick() }) {
                        Text(
                            text = "Volver",
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                    }
                    if (!esInvitado) {
                        Spacer(modifier = Modifier.size(8.dp))
                        Button(
                            modifier = Modifier
                                .size(width = 139.dp, height = 60.dp)
                                .border(
                                    width = 1.dp,
                                    shape = RoundedCornerShape(12.dp),
                                    color = Color.Black
                                ),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.yellow)),
                            onClick = { onClickAforo() }) {
                            Text(
                                text = "Ver aforo",
                                fontSize = 18.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }

}