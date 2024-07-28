package org.convert.convercy.ui.screens.main_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.convert.convercy.data.interactors.main_interactor.MainInteractor
import org.convert.convercy.settings.MAIN_LOG_TAG

class MainScreenViewModel(
    private val mainInteractor: MainInteractor,
    private val stateHolder: MainScreenStateHolder
) : ViewModel() {
    val screenState: MainScreenContract.State
        get() = stateHolder.screenState
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, error ->
        Log.e(MAIN_LOG_TAG, "MainViewModel error!: ${error.message}.")
        error.printStackTrace()
    }
    private val mainScope =
        CoroutineScope(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler)
    private var mainInteractorHandlerJob: Job? = null

    init {
        setMainInteractorHandler()
    }

    private fun setMainInteractorHandler() {
        mainInteractorHandlerJob?.cancel()
        mainInteractorHandlerJob = mainScope.launch {
            mainInteractor.outFlow.collect { message ->
                launch(Dispatchers.Main) {
                    stateHolder.update(message)
                }
            }
        }
    }

    fun handleEvent(event: MainScreenContract.Event) {
        when (event) {
            is MainScreenContract.Event.RequestDailyRates ->
                mainInteractor.requestDailyRates()

            is MainScreenContract.Event.Reconnect ->
                mainInteractor.requestDailyRates()

            is MainScreenContract.Event.SwapCurrencies ->
                mainInteractor.onSwapCurrenciesEvent()

            is MainScreenContract.Event.FromCurrencyTypeChanged ->
                mainInteractor.onFromCurrencyTypeChangedEvent(event.newFromCurrencyType)

            is MainScreenContract.Event.FromCurrencyAmountChanged ->
                mainInteractor.onFromCurrencyAmountChangedEvent(event.newFromCurrencyAmount)

            is MainScreenContract.Event.ToCurrencyTypeChanged ->
                mainInteractor.onToCurrencyTypeChangedEvent(event.newToCurrencyType)
        }
    }
}