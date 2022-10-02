package com.example.myapplication.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppTextField(label: String, value: String) {
    TextField(
        modifier = Modifier
            .width(292.dp)
            .height(80.dp)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(16.dp),
                color = Color.Black
            ),
        shape = RoundedCornerShape(16.dp),
        value = value,
        enabled = true,
        onValueChange = {},
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
        label = { Text(text = label, fontSize = 16.sp) },
        singleLine = true,
    )
}