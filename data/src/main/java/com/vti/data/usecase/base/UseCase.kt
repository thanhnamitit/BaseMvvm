package com.vti.data.usecase.base

interface UseCase {
    val isRunning: Boolean
    fun cancel()
    fun cancelIfRunning()
}