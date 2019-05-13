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
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.concurrent.TimeUnit

class GpsStatusObservable(val context: Context) : NaviLiveData<Boolean>(), LifecycleObserver {
    var isRegistered = false
    val publishSubject: PublishSubject<Boolean> = PublishSubject.create()
    var disposable: Disposable? = null
    val gpsStatusReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (LocationManager.PROVIDERS_CHANGED_ACTION == intent.getAction()) {
                setProperValue()
            }
        }
    }

    init {
    }

    fun start() {
        if (isRegistered) {
            return
        }
        isRegistered = true
        val intentFilter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        context.registerReceiver(gpsStatusReceiver, intentFilter)
        disposable = publishSubject.debounce(1, TimeUnit.SECONDS).subscribe { postValue(it) }
        setProperValue()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun stop() {
        if (!isRegistered) {
            return
        }
        try {
            context.unregisterReceiver(gpsStatusReceiver)
        } catch (e: Exception) {
        }
        disposable?.dispose()
    }

    fun setProperValue() {
        Timber.d("onReceive: PROVIDERS_CHANGED_ACTION $value")
        publishSubject.onNext(SystemUtil.isGpsEnable(context))
    }
}