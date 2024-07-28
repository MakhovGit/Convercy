package org.convert.convercy.repository

import kotlinx.coroutines.flow.Flow
import org.convert.convercy.data.dto.DailyRatesDto

interface NetworkRepository {
    suspend fun getDailyRates(): Flow<DailyRatesDto>
}