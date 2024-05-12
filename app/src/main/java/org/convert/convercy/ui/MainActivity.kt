package org.convert.convercy.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.convert.convercy.ui.screens.ExchangeScreen
import org.convert.convercy.ui.theme.ConvercyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConvercyTheme {
                ExchangeScreen()
            }
        }
    }
}
