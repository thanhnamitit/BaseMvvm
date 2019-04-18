package com.vti.location.component

import android.location.Location
import com.vti.base.extension.livedata.NaviLiveData
import com.vti.base.mvvm.viewmodel.BaseViewModel

open class LocationViewModel : BaseViewModel() {
    val location = NaviLiveData<Location>()
    val locationAvailable = NaviLiveData<Boolean>()
}