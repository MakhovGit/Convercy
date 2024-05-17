package org.convert.convercy.model.intent

import kotlinx.coroutines.flow.SharedFlow

interface IntentContract<T, U> {
    val screenStateFlow: SharedFlow<T>
    fun handleEvent()
    fun handleEvent(screenEvent: U)
}