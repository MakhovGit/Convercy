package org.convert.convercy.ui.screens

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.convert.convercy.intent.MainIntent
import org.convert.convercy.model.intent.IntentContract
import org.convert.convercy.model.screens.ScreenEvents
import org.convert.convercy.model.screens.ScreenStates
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnrememberedMutableState", "SuspiciousIndentation")
@Composable
fun MainScreen() {
    val mainIntent: IntentContract<ScreenStates, ScreenEvents> = koinViewModel<MainIntent>()
    CompositionLocalProvider(
        androidx.lifecycle.compose.LocalLifecycleOwner provides androidx.compose.ui.platform.LocalLifecycleOwner.current,
    ) {
        val screenStateFlow by mainIntent.screenStateFlow
            .collectAsStateWithLifecycle(initialValue = ScreenStates.SplashScreenState)
           when (screenStateFlow) {
           is ScreenStates.SplashScreenState -> SplashScreen()
           is ScreenStates.EventScreenState -> EventScreen(mainIntent)
           is ScreenStates.ExchangeScreenState -> ExchangeScreen(
               mainIntent,
               screenStateFlow as ScreenStates.ExchangeScreenState
           )
        }
    }
}