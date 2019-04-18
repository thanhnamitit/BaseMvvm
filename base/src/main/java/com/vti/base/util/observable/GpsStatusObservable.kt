package com.vti.base.util.observable

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.vti.base.extension.livedata.NaviLiveData
import com.vti.base.util.SystemUtil
import timber.log.Timber

class GpsStatusObservable(val context: Context) : NaviLiveData<Boolean>(), LifecycleObserver {
    var isRegistered = false

    val gpsStatusReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (LocationManager.PROVIDERS_CHANGED_ACTION == intent.getAction()) {
                setProperValue()
                Timber.d("onReceive: PROVIDERS_CHANGED_ACTION $value")
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun startObserveToGpsStatus() {
        if (isRegistered) {
            return
        }
        isRegistered = true
        val intentFilter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        context.registerReceiver(gpsStatusReceiver, intentFilter)
        setProperValue()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun stopObserveToGpsStatus() {
        if (!isRegistered) {
            return
        }
        context.unregisterReceiver(gpsStatusReceiver)
    }

    fun setProperValue() {
        setValue(SystemUtil.isGpsEnable(context))
    }
}