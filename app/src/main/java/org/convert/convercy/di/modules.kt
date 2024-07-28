package org.convert.convercy.di

import org.convert.convercy.data.interactors.main_interactor.MainInteractor
import org.convert.convercy.data.interactors.network_interactor.NetworkInteractor
import org.convert.convercy.data.retrofit.RetrofitDatasource
import org.convert.convercy.repository.NetworkRepository
import org.convert.convercy.repository.NetworkRepositoryImpl
import org.convert.convercy.ui.screens.main_screen.MainScreenReducer
import org.convert.convercy.ui.screens.main_screen.MainScreenStateHolder
import org.convert.convercy.ui.screens.main_screen.MainScreenViewModel
import org.convert.convercy.utils.DataUtils
import org.convert.convercy.utils.NetworkMapper
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appKoinModule = module {
    singleOf(::RetrofitDatasource)
    single<NetworkRepository> { NetworkRepositoryImpl(retrofitDatasource = get()) }
    singleOf(::NetworkMapper)
    singleOf(::NetworkInteractor)
    singleOf(::DataUtils)
    singleOf(::MainScreenReducer)
    singleOf(::MainScreenStateHolder)
    singleOf(::MainInteractor)
    viewModelOf(::MainScreenViewModel)
}