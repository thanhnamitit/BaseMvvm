package com.vti.base.provider

import androidx.lifecycle.LifecycleOwner

interface SimpleLifecycleOwnerProvider {
    // only provide the lifecycle that observe onCreate() and onDestroy() methods
    fun getSimpleLifecycleOwner(): LifecycleOwner

    fun getNormalLifecycleOwner(): LifecycleOwner
}
