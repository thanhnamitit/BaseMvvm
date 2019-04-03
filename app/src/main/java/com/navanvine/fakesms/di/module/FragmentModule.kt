package com.navanvine.fakesms.di.module

import com.navanvine.fakesms.screen.main.FakeSmsFragment
import com.navanvine.fakesms.screen.splash.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun bindSplashFragment(): SplashFragment

    @ContributesAndroidInjector
    abstract fun bindFakeSmsFragment(): FakeSmsFragment
}
