package org.convert.convercy.data.interactors

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import org.convert.convercy.model.interactors.NetworkInteractorMessages
import org.convert.convercy.repository.NetworkRepository
import org.convert.convercy.settings.MAIN_LOG_TAG
import org.convert.convercy.utils.NetworkMapper
import org.convert.convercy.utils.ONE

class NetworkInteractor(
    private val repository: NetworkRepository,
    private val mapper: NetworkMapper
) {
    private val _outFlow: MutableSharedFlow<NetworkInteractorMessages> =
        MutableSharedFlow(replay = Int.ONE)
    val outFlow = _outFlow.asSharedFlow()
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, error ->
        Log.e(MAIN_LOG_TAG, "Network interactor error! $error")
        error.printStackTrace()
    }
    private val mainScope =
        CoroutineScope(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler)

    fun start() {
        mainScope.launch {
            _outFlow.emit(NetworkInteractorMessages.Loading)
            repository.getDailyRates()
                .flowOn(Dispatchers.IO)
                .retry(MAX_RETRY_ATTEMPTS) { error ->
                    Log.e(MAIN_LOG_TAG, "Network request error: ${error.message}. Retry")
                    delay(RETRY_DELAY)
                    true
                }
                .catch { error ->
                    Log.e(MAIN_LOG_TAG, "Network error. Stop.")
                    _outFlow.emit(NetworkInteractorMessages.Error(error))
                }
                .collect { result ->
                    _outFlow.emit(NetworkInteractorMessages.Success(mapper.map(result)))
                }
        }
    }

    companion object {
        private const val MAX_RETRY_ATTEMPTS = 6L
        private const val RETRY_DELAY = 1000L
    }
}