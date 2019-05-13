package com.vti.base.extension.livedata

import androidx.lifecycle.Observer

class OnceObserver<T>(val callBack: (t: T) -> Unit, var liveData: NaviLiveData<T>) : Observer<T> {
    override fun onChanged(t: T) {
        liveData.removeObserver(this)
        callBack.invoke(t)
    }

}