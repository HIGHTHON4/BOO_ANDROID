package com.example.boo_android

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyMessageBubble(message: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .align(Alignment.End)
                .padding(start = 60.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                .background(Color(0xFF458CE5), RoundedCornerShape(16.dp))
                .padding(vertical = 10.dp, horizontal = 12.dp)
        ) {
            Text(text = message, color = Color.White, fontSize = 12.sp)
        }
    }
}

@Composable
fun ReplyMessageBubble(message: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 8.dp, end = 60.dp, top = 4.dp, bottom = 4.dp)
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(vertical = 10.dp, horizontal = 12.dp)
        ) {
            Text(text = message, color = Color.Black, fontSize = 12.sp)
        }
    }
}
