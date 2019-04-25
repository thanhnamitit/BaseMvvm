package com.vti.base.extension.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.vti.base.provider.SimpleLifecycleOwnerProvider

open class NaviLiveData<T> : LiveData<T>() {


    public override fun postValue(value: T) {
        super.postValue(value)
    }

    public override fun setValue(value: T) {
        super.setValue(value)
    }

    fun observeUntilDestroyed(lifecycleOwnerProvider: SimpleLifecycleOwnerProvider, observer: Observer<T>) {
        observe(lifecycleOwnerProvider.getSimpleLifecycleOwner(), observer)
    }

    fun reObserve(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        removeObservers(lifecycleOwner)
        observe(lifecycleOwner, observer)
    }

    @Deprecated("This method can make LiveData reference to OnceObserver forever if there is no data set, lead to leak memory. Use observeOnce(lifecycleOwner: LifecycleOwner, observer: OnceObserver) method instead")
    fun observeOnce(observer: OnceObserver<T>) {
        observer.liveData = this
        observeForever(observer)
    }

    fun observeOnce(lifecycleOwner: LifecycleOwner, observer: OnceObserver<T>) {
        observer.liveData = this
        observe(lifecycleOwner, observer)
    }

}