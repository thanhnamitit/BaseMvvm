package com.vti.location.extension

import android.location.Location
import com.vti.base.extension.livedata.NaviLiveData
import com.vti.location.data.model.LocationWrapper

class LocationProvider(val statusObserver: ((isActive: Boolean) -> Unit)? = null) : NaviLiveData<LocationWrapper>() {

    var location: Location? = null
        set(value) {
            setValue(LocationWrapper.fromLocation(value!!))
            field = value
        }
    var status: Int = 0
        set(value) {
            setValue(LocationWrapper.fromStatus(value))
        }

    override fun onActive() {
        super.onActive()
        statusObserver?.invoke(true)
    }

    override fun onInactive() {
        super.onInactive()
        statusObserver?.invoke(false)
    }


}