package org.convert.convercy.di

import org.convert.convercy.data.keepers.DailyRatesKeeper
import org.convert.convercy.data.interactors.NetworkInteractor
import org.convert.convercy.data.retrofit.RetrofitDatasource
import org.convert.convercy.intent.MainIntent
import org.convert.convercy.repository.NetworkRepository
import org.convert.convercy.repository.NetworkRepositoryImpl
import org.convert.convercy.utils.NetworkMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appKoinModule = module {
    single { RetrofitDatasource() }
    single<NetworkRepository> { NetworkRepositoryImpl(retrofitDatasource = get()) }
    single { NetworkMapper() }
    single { NetworkInteractor(repository = get(), mapper = get()) }
    single { DailyRatesKeeper(networkInteractor = get()) }
    viewModel { MainIntent(networkInteractor = get(), dailyRatesKeeper = get()) }
}