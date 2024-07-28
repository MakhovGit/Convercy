package org.convert.convercy.ui.screens.main_screen

import org.convert.convercy.data.interactors.main_interactor.MainInteractorMessages

class MainScreenReducer {

    private fun onRequestDailyRates(
        currentState: MainScreenContract.State,
        result: MainInteractorMessages.RequestDailyRates
    ): MainScreenContract.State {
        return when (result) {
            is MainInteractorMessages.RequestDailyRates.Loading -> currentState.copy(
                isDataLoading = true,
                error = null
            )

            is MainInteractorMessages.RequestDailyRates.Success -> currentState.copy(
                isDataLoading = false,
                error = null,
                currencyInfo = result.currencyInfo
            )

            is MainInteractorMessages.RequestDailyRates.Error -> currentState.copy(
                isDataLoading = false,
                error = result
            )
        }
    }

    private fun onFromCurrencyAmountChanged(
        currentState: MainScreenContract.State,
        result: MainInteractorMessages.FromCurrencyAmountChanged
    ): MainScreenContract.State {
        return currentState.copy(
            currencyInfo = result.currencyInfo
        )
    }

    private fun onFromCurrencyTypeChanged(
        currentState: MainScreenContract.State,
        result: MainInteractorMessages.FromCurrencyTypeChanged
    ): MainScreenContract.State {
        return currentState.copy(
            currencyInfo = result.currencyInfo
        )
    }

    private fun onSwapCurrencies(
        currentState: MainScreenContract.State,
        result: MainInteractorMessages.SwapCurrencies
    ): MainScreenContract.State {
        return currentState.copy(
            currencyInfo = result.currencyInfo
        )
    }

    private fun onToCurrencyTypeChanged(
        currentState: MainScreenContract.State,
        result: MainInteractorMessages.ToCurrencyTypeChanged
    ): MainScreenContract.State {
        return currentState.copy(
            currencyInfo = result.currencyInfo
        )
    }

    fun reduce(
        currentState: MainScreenContract.State,
        result: MainInteractorMessages
    ): MainScreenContract.State {
        return when (result) {
            is MainInteractorMessages.RequestDailyRates -> onRequestDailyRates(currentState, result)
            is MainInteractorMessages.FromCurrencyAmountChanged -> onFromCurrencyAmountChanged(
                currentState, result
            )

            is MainInteractorMessages.FromCurrencyTypeChanged -> onFromCurrencyTypeChanged(
                currentState, result
            )

            is MainInteractorMessages.SwapCurrencies -> onSwapCurrencies(currentState, result)
            is MainInteractorMessages.ToCurrencyTypeChanged -> onToCurrencyTypeChanged(
                currentState, result
            )
        }
    }
}