package org.convert.convercy.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import org.convert.convercy.R
import org.convert.convercy.ui.theme.ProgressIndicatorColor
import org.convert.convercy.ui.theme.ScreenBackgroundColor

@Composable
fun SplashScreen() {
    val imageWeight = 2.0F
    val progressWeight = 1.0F
    Surface(
        color = ScreenBackgroundColor,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = null,
                alignment = Alignment.BottomCenter,
                modifier = Modifier
                    .weight(imageWeight)
                    .fillMaxSize()
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.weight(progressWeight)
            ) {
                CircularProgressIndicator(color = ProgressIndicatorColor)
            }
        }
    }
}