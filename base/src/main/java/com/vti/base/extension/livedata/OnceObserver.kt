package com.vti.base.extension.livedata

import androidx.lifecycle.Observer

abstract class OnceObserver<T> : Observer<T> {
    lateinit var liveData: NaviLiveData<T>
    override fun onChanged(t: T) {
        liveData.removeObserver(this)
    }
}