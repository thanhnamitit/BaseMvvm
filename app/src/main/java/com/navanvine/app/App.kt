package com.navanvine.app

import com.navanvine.app.di.viewModelModule
import com.vti.base.application.BaseApplication
import com.vti.base.di.module.baseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(viewModelModule, baseModule)
        }
    }
}