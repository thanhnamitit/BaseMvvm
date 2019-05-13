package com.vti.location.component

import android.location.Location
import com.vti.base.extension.livedata.NaviLiveData
import com.vti.base.viewmodel.BaseViewModel

open class LocationViewModel : BaseViewModel() {
    val location = NaviLiveData<Location>()
    val locationAvailable = NaviLiveData<Boolean>()
    val locationObserveFlag = NaviLiveData<Boolean>()
    fun startListeningToLocation() = locationObserveFlag.value.let {
        if (it == null || it == false) {
            locationObserveFlag.setValue(true)
        }

    }
    fun stopListeningToLocation() = locationObserveFlag.value?.let {
        if (it == true) locationObserveFlag.setValue(true)
    }
    fun isListeningToLocation() = locationObserveFlag.value.let { it != null && it == true }
}