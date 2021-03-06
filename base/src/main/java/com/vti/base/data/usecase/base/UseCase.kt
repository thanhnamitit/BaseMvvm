package com.vti.base.data.usecase.base

import org.koin.core.KoinComponent

interface UseCase : KoinComponent{
    val isRunning: Boolean
    fun cancel()
    fun cancelIfRunning()
}