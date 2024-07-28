package org.convert.convercy.ui.screens.main_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.convert.convercy.data.interactors.main_interactor.MainInteractorMessages

class MainScreenStateHolder(
    private val reducer: MainScreenReducer
) {
    var screenState by mutableStateOf(MainScreenContract.State())
        private set

    fun update(result: MainInteractorMessages) {
        screenState = reducer.reduce(screenState, result)
    }
}