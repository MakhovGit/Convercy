package org.convert.convercy.model.intent

import kotlinx.coroutines.flow.StateFlow

interface IntentContract<T, U> {
    val screenStateFlow: StateFlow<T>
    fun handleEvent(screenEvent: U)
}