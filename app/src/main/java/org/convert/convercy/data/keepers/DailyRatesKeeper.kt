package org.convert.convercy.data.keepers

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.convert.convercy.data.interactors.NetworkInteractor
import org.convert.convercy.model.info.DailyRatesInfo
import org.convert.convercy.model.interactors.NetworkInteractorMessages
import org.convert.convercy.settings.MAIN_LOG_TAG
import org.convert.convercy.utils.ONE

class DailyRatesKeeper(
    private val networkInteractor: NetworkInteractor
) {
    private val _outFlow: MutableSharedFlow<DailyRatesInfo> =
        MutableSharedFlow(replay = Int.ONE)
    val outFlow = _outFlow.asSharedFlow()
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, error ->
        Log.e(MAIN_LOG_TAG, "DailyRatesKeeper error!: ${error.message}.")
        error.printStackTrace()
    }
    private val mainScope =
        CoroutineScope(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler)
    private lateinit var dailyRatesInfo: DailyRatesInfo

    init {
        setNetworkInteractorListener()
    }

    private fun setNetworkInteractorListener() {
        mainScope.launch {
            networkInteractor.outFlow
                .collect { result ->
                    if (result is NetworkInteractorMessages.Success) {
                        dailyRatesInfo = result.dailyRatesInfo
                        _outFlow.emit(dailyRatesInfo)
                    }
                }
        }
    }

}
