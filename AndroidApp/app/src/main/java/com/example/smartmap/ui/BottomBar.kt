package com.example.smartmap.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.smartmap.R

@Composable
fun BottomBar(
    onClickCs: () -> Unit,
    onClickBuscar: () -> Unit,
    onClickCentrar: () -> Unit
) {
    BottomAppBar(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = colorResource(id = R.color.blue),
        contentPadding = PaddingValues(horizontal = 40.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onClickBuscar() }) { // (2.1)
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable { onClickBuscar() },
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Buscar",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.padding(40.dp))
            IconButton(onClick = { onClickCentrar() }) { // (2.1)
                Image(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable { onClickCentrar() },
                    painter = painterResource(id = R.drawable.center),
                    contentDescription = "Centrar",
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
            Spacer(modifier = Modifier.padding(40.dp))
            IconButton(onClick = { onClickCs() }) { // (2.1)
                Image(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable { onClickCs() },
                    painter = painterResource(id = R.drawable.cerrarsesion),
                    contentDescription = "Cerrar",
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }

        }
    }
}