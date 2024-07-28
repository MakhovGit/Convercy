package org.convert.convercy.data.interactors.network_interactor

import org.convert.convercy.model.info.DailyRatesInfo

sealed interface NetworkInteractorMessages {
    sealed class RequestDailyRates : NetworkInteractorMessages {
        data object Loading : RequestDailyRates()
        data class Error(val error: Throwable) : RequestDailyRates()
        data class Success(val dailyRatesInfo: DailyRatesInfo) : RequestDailyRates()
    }

}