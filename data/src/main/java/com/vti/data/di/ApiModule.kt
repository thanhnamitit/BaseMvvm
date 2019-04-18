package com.vti.data.di

import com.vti.data.fetcher.Fetcher
import org.koin.dsl.module

object ApiModule {
    val fetcher = module {
        single { Fetcher() }
    }
}
