package org.convert.convercy.data.retrofit

import com.google.gson.GsonBuilder
import org.convert.convercy.data.dto.DailyRatesDTO
import org.convert.convercy.settings.RATES_URL_BASE
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitDatasource {

    private val retrofitApi = Retrofit
        .Builder()
        .baseUrl(RATES_URL_BASE)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()
        .create(RetrofitApi::class.java)

    suspend fun getDailyRates(): DailyRatesDTO =
        retrofitApi.getDailyRates()
}