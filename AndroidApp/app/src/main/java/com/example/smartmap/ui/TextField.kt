package com.example.smartmap.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppTextField(
    label: String,
    value: String,
    onTextChange: (String) -> Unit,
    hide: Boolean,
    Icon: @Composable (() -> Unit)? = null
) {
    TextField(
        modifier = Modifier
            .width(320.dp)
            .wrapContentHeight()
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(24.dp),
                color = Color.Black
            ),
        shape = RoundedCornerShape(24.dp),
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
        leadingIcon = Icon,
        label = { Text(text = label, fontSize = 16.sp) },
        singleLine = true,
        visualTransformation = if (hide) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        textStyle = TextStyle(fontSize = 16.sp)
    )
}

@Composable
fun Mail() {
    Icon(
        imageVector = Icons.Filled.Email,
        contentDescription = "Email"
    )
}

@Composable
fun Password() {
    Icon(
        imageVector = Icons.Filled.Lock,
        contentDescription = "Password"
    )
}

@Composable
fun Nombre() {
    Icon(
        imageVector = Icons.Filled.Person,
        contentDescription = "Name"
    )
}