package org.convert.convercy.ui.screens

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.convert.convercy.intent.MainIntent
import org.convert.convercy.model.intent.IntentContract
import org.convert.convercy.model.screens.ScreenEvents
import org.convert.convercy.model.screens.ScreenStates
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun MainScreen() {
    var isFirstStart by remember {mutableStateOf(true )}
    val mainIntent: IntentContract<ScreenStates, ScreenEvents> = koinViewModel<MainIntent>()
    val screenStateFlow by mainIntent.screenStateFlow
        .collectAsStateWithLifecycle(initialValue = ScreenStates.SplashScreen)
    key(screenStateFlow) {
        when (screenStateFlow) {
            is ScreenStates.SplashScreenState -> SplashScreen()
            is ScreenStates.EventScreenState -> EventScreen(mainIntent)
            is ScreenStates.ExchangeScreenState -> ExchangeScreen(
                mainIntent,
                screenStateFlow as ScreenStates.ExchangeScreen
            )
            else -> {}
        }
    }
    if (isFirstStart) {
        isFirstStart = isFirstStart.not()
        mainIntent.handleEvent()
    }
}