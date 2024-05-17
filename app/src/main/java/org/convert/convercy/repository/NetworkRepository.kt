package org.convert.convercy.repository

import kotlinx.coroutines.flow.Flow
import org.convert.convercy.data.dto.DailyRatesDTO

interface NetworkRepository {
    suspend fun getDailyRates(): Flow<DailyRatesDTO>
}