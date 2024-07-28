package org.convert.convercy.ui.screens.main_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.convert.convercy.ui.screens._elements.EventScreen
import org.convert.convercy.ui.screens._elements.ExchangeScreen
import org.convert.convercy.ui.screens._elements.SplashScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen() {
    val viewModel = koinViewModel<MainScreenViewModel>()
    val state = viewModel.screenState
    var isRequestSent by remember { mutableStateOf(false) }
    if (isRequestSent.not()) {
        isRequestSent = true
        viewModel.handleEvent(MainScreenContract.Event.RequestDailyRates)
    }
    Scaffold(
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                when {
                    state.isDataLoading -> SplashScreen()
                    state.error != null -> EventScreen(
                        onClick = {
                            viewModel.handleEvent(MainScreenContract.Event.Reconnect)
                        }
                    )

                    else -> ExchangeScreen(
                        screenState = state,
                        exchangeCardOnClick = {
                            viewModel.handleEvent(MainScreenContract.Event.SwapCurrencies)
                        },
                        onCurrencyAmountClick = { isReadOnlyMode, currency ->
                            viewModel.handleEvent(
                                if (isReadOnlyMode.not())
                                    MainScreenContract.Event.FromCurrencyTypeChanged(currency)
                                else
                                    MainScreenContract.Event.ToCurrencyTypeChanged(currency)
                            )
                        },
                        onCurrencyAmountValueChange = { value: String ->
                            viewModel.handleEvent(
                                MainScreenContract.Event.FromCurrencyAmountChanged(value)
                            )
                        }
                    )
                }
            }
        }
    )
}