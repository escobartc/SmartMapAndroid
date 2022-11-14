package com.example.smartmap.ui

import androidx.compose.runtime.Composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartmap.R

@Composable
fun ChartScreen(
    data: List<Pair<Int, Double>> = emptyList(),
    onClick: () -> Unit,
    edificio: String,
    numero: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 30.dp),
            text = "Aforo 7am-7pm en $edificio",
            fontSize = 22.sp,
            overflow = TextOverflow.Visible,
            maxLines = 2,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            color = Color.Black
        )
        QuadLineChart(
            data = data
        )
        Spacer(modifier = Modifier.size(24.dp))
        Row(horizontalArrangement = Arrangement.Center) {
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
        }

    }
}