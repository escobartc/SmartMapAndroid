package com.example.myapplication.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@Preview
@Composable
fun AppTitle() {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 60.dp, vertical = 16.dp)) {
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 36.sp,
            color = Color.Cyan
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            modifier = Modifier.padding(start = 90.dp),
            text = stringResource(id = R.string.javeriana),
            fontSize = 36.sp,
            color = Color.White
        )
    }
}
