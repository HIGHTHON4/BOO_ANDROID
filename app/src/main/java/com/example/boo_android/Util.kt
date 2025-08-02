package com.example.boo_android

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun BooTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    modifier: Modifier = Modifier,
    sendEnabled: Boolean = true,
    onSendClick: () -> Unit = {},
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholderText) },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFF2A3342),
            focusedContainerColor = Color(0xFF2A3342),
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            unfocusedTextColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedPlaceholderColor = Color(0xFFC3C7CF),
            focusedPlaceholderColor = Color(0xFFC3C7CF)
        ),
        shape = RoundedCornerShape(24.dp),
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 8.dp),
        trailingIcon = {
            if (sendEnabled) {
                IconButton(
                    onClick = onSendClick
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_send),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }

    )
}