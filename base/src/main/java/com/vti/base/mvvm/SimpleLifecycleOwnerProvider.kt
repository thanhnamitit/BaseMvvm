package com.vti.base.mvvm

import androidx.lifecycle.LifecycleOwner

interface SimpleLifecycleOwnerProvider {
    // only provide the lifecycle that observe onCreate() and onDestroy() methods
    fun getSimpleLifecycleOwner(): LifecycleOwner

    fun getNormalLifecycleOwner(): LifecycleOwner
}
