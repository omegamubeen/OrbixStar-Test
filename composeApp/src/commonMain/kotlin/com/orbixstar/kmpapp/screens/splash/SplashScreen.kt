package com.orbixstar.kmpapp.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import orbixstar_coding_challenge.composeapp.generated.resources.Res
import orbixstar_coding_challenge.composeapp.generated.resources.logo
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource

@Composable
fun SplashScreen(navigateToMain: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(3_000)
        navigateToMain()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier.size(150.dp)
        )
    }
}
