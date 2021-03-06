package com.navanvine.app.di

import com.navanvine.app.screen.MainViewModel
import com.navanvine.app.screen.main.FakeSmsViewModel
import com.navanvine.app.screen.splash.SplashViewModel
import com.vti.base.view.dialog.permission.RequestPermissionViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel() }
    viewModel { SplashViewModel() }
    viewModel { FakeSmsViewModel() }
    viewModel { RequestPermissionViewModel() }
}
