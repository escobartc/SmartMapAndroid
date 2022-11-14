package com.example.smartmap.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartmap.R

@Preview
@Composable
fun AppTitle() {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp, vertical = 16.dp)) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.app_name),
            fontSize = 34.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            modifier = Modifier.fillMaxWidth().padding(start = 90.dp),
            text = stringResource(id = R.string.javeriana),
            fontSize = 36.sp,
            color = Color.White
        )
    }
}
