package org.convert.convercy.model.interactors

import org.convert.convercy.model.info.DailyRatesInfo

sealed interface NetworkInteractorMessages {
    data object Loading: NetworkInteractorMessages
    data class Error(
        val error: Throwable
    ): NetworkInteractorMessages
    data class Success(
        val dailyRatesInfo: DailyRatesInfo
    ): NetworkInteractorMessages

}