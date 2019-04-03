package com.navanvi.base.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.navanvi.base.extension.livedata.Event

open class BaseViewModel : ViewModel() {
    val eventNavigator = MutableLiveData<Event<Int>>()

    fun sendEvent(value: Int) {
        eventNavigator.postValue(Event(value))
    }


}
