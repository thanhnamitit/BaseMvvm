package com.navanvine.fakesms.di.module

import androidx.lifecycle.ViewModel
import com.vti.base.di.viewmodel.ViewModelKey
import com.navanvine.fakesms.screen.MainViewModel
import com.navanvine.fakesms.screen.main.FakeSmsViewModel
import com.navanvine.fakesms.screen.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FakeSmsViewModel::class)
    abstract fun bindFakeSmsViewModel(viewModel: FakeSmsViewModel): ViewModel
}