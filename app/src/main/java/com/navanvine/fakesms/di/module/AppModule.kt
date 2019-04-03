package com.navanvine.fakesms.di.module

import android.content.Context
import com.navanvine.fakesms.di.App
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {
    @Binds
    internal abstract fun bindContext(app: App): Context
}
