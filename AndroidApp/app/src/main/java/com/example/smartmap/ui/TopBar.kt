package com.example.smartmap.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
internal fun DetailTopAppBar(
    value: String,
    onClickBuscar: () -> Unit,
    onTextChange: (String) -> Unit,
    onClickClean: () -> Unit
) {
    Row( Modifier.background(Color.Transparent).padding(horizontal = 16.dp)
    ) {
        Column(Modifier.wrapContentSize()) {
            BuscarEd(
                value = value,
                onTextChange = { onTextChange(it) },
                onClickBuscar = { onClickBuscar() },
                onClickClean = { onClickClean() })
        }
    }
}