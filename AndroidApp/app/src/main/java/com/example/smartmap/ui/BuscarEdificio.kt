package com.example.smartmap.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartmap.R

@Composable
fun BuscarEd(
    value: String,
    onTextChange: (String) -> Unit,
    onClickBuscar: () -> Unit,
    onClickClean: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Row(horizontalArrangement = Arrangement.Center) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(16.dp),
                    color = Color.Black
                ),
            shape = RoundedCornerShape(16.dp),
            value = value,
            enabled = true,
            onValueChange = { onTextChange(it) },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray,
            ),
            placeholder = { Text(text = stringResource(id = R.string.busqueda), fontSize = 16.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)  },
            leadingIcon = {
                Icon(
                    modifier = Modifier.clickable { onClickClean() },
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Limpiar"
                )
            },
            trailingIcon = {
                Icon(
                    modifier = Modifier.clickable { onClickBuscar() },
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Buscar"
                )
            },
            singleLine = true,
            textStyle = TextStyle(fontSize = 16.sp),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
        )

    }

}