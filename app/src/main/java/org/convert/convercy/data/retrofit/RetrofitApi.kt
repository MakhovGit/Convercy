package org.convert.convercy.data.retrofit

import org.convert.convercy.data.dto.DailyRatesDto
import org.convert.convercy.settings.RATES_URL_JSON
import retrofit2.http.GET

interface RetrofitApi {
    @GET(RATES_URL_JSON)
    suspend fun getDailyRates(): DailyRatesDto
}