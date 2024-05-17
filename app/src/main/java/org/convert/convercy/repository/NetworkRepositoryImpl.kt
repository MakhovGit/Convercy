package org.convert.convercy.repository

import kotlinx.coroutines.flow.flow
import org.convert.convercy.data.retrofit.RetrofitDatasource

class NetworkRepositoryImpl(
    private val retrofitDatasource: RetrofitDatasource
) : NetworkRepository {
    override suspend fun getDailyRates() = flow {
        emit(retrofitDatasource.getDailyRates())
    }
}