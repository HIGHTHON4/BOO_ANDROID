package com.example.boo_android

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    delayTime: Long = 1500L
) {
    LaunchedEffect(Unit) {
        delay(delayTime)
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.splash),
            contentDescription = "spalsh"
        )
    }
}