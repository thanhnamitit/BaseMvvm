package com.vti.driverapp.di

import com.vti.driverapp.screen.MainViewModel
import com.vti.driverapp.screen.map.MapViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel() }
    viewModel { MapViewModel() }
}