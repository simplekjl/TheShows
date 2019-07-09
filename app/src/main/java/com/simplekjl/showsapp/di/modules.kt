package com.simplekjl.showsapp.di

import com.simplekjl.showsapp.BuildConfig
import com.simplekjl.showsapp.data.remote.MoviesDBService
import com.simplekjl.showsapp.data.remote.MoviesDBServiceFactory
import com.simplekjl.showsapp.data.remote.Network
import com.simplekjl.showsapp.data.remote.NetworkImpl
import com.simplekjl.showsapp.domain.Repository
import com.simplekjl.showsapp.domain.RepositoryImpl
import com.simplekjl.showsapp.presentation.features.showlist.ShowListActivityViewModel
import com.simplekjl.showsapp.presentation.features.showlist.mapper.StateMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit


val appModule = module(override = true) {

    single<MoviesDBService> { get<Retrofit>().create(MoviesDBService::class.java) }
    single<Network> { NetworkImpl(get()) }
    single<Repository> { RepositoryImpl(NetworkImpl(get())) }
    single{ StateMapper() }
    viewModel { ShowListActivityViewModel(get(), get()) }
    factory { MoviesDBServiceFactory.makeMoviesDbService(BuildConfig.DEBUG) }
}