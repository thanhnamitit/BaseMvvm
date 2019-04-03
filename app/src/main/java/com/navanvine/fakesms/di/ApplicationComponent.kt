package com.navanvine.fakesms.di

import com.navanvi.base.di.module.ViewModelFactoryModule
import com.navanvine.fakesms.di.module.ActivityModule
import com.navanvine.fakesms.di.module.AppModule
import com.navanvine.fakesms.di.module.FragmentModule
import com.navanvine.fakesms.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityModule::class,
        FragmentModule::class,
        ViewModelModule::class,
        ViewModelFactoryModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<App> {
    override fun inject(application: App)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun build(): ApplicationComponent
    }
}