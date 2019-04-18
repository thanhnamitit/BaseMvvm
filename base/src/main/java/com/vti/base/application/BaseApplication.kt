package com.vti.base.application

import android.app.Application
import com.vti.base.util.timber.DebugReportTree
import timber.log.Timber

abstract class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugReportTree())

    }
}