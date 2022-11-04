package com.example.smartmap.ui

import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.smartmap.R


@Composable
internal fun DetailTopAppBar(value: String, onClickBuscar: () -> Unit, onTextChange: (String) -> Unit, onClickCs: () -> Unit) {
    TopAppBar(
        title = {
            BuscarEd(
                value = value,
                onTextChange = { onTextChange(it) },
                onClickBuscar = { onClickBuscar() })
        },
        actions = {
            IconButton(onClick = { onClickCs() }) { // (2.1)
                Icon(
                    modifier = Modifier.clickable { onClickCs() },
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Buscar",
                    tint = Color.White
                )
            }
        },
        backgroundColor = colorResource(id = R.color.blue)
    )
}