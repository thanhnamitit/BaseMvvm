package com.vti.base.data.di

import com.vti.base.data.fetcher.Fetcher
import org.koin.dsl.module

object ApiModule {
    val fetcher = module {
        single { Fetcher() }
    }
}
