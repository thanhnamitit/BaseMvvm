package com.vti.base.mvvm.viewmodel

import androidx.lifecycle.*
import com.vti.base.extension.livedata.NaviLiveData
import com.vti.base.extension.livedata.event.Event
import com.vti.base.mvvm.SimpleLifecycleOwnerProvider
import org.koin.core.KoinComponent

open class BaseViewModel : ViewModel(), LifecycleObserver, LifecycleOwner, SimpleLifecycleOwnerProvider, KoinComponent {
    private val lifecycleRegistry by lazy { LifecycleRegistry(this) }

    val normalEventNavigator = NaviLiveData<Event<Int>>()
    val expressEventNavigator = NaviLiveData<Event<Int>>()
    var observerHasBeenSetup = false
    var destroyed = false
    var inited = false
    var isForeground = false
        private set

    open fun setupObserver() {
    }

    fun hasNotSetupYet(): Boolean {
        return if (inited) {
            false
        } else {
            inited = true
            false
        }
    }

    fun sendEvent(value: Int) {
        normalEventNavigator.setValue(Event(value))
    }

    fun postEvent(value: Int) {
        normalEventNavigator.postValue(Event(value))
    }

    fun sendEventImmediately(value: Int) {
        expressEventNavigator.setValue(Event(value))
    }

    fun postEventImmediately(value: Int) {
        expressEventNavigator.postValue(Event(value))
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun doOnCreate() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun checkToSetupObserver() {
        destroyed = false
        if (!observerHasBeenSetup) setupObserver()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun doOnResume() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        isForeground = true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun doOnPause() {
        isForeground = false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun doOnStop() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun doOnDestroy() {
        destroyed = true
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    override fun getSimpleLifecycleOwner(): LifecycleOwner = this
    override fun getNormalLifecycleOwner(): LifecycleOwner = this
}
