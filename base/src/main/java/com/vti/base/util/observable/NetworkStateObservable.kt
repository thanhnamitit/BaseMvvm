package com.vti.base.util.observable

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.vti.base.extension.livedata.NaviLiveData
import com.vti.base.util.SystemUtil

/**
 * https://stackoverflow.com/questions/36421930/connectivitymanager-connectivity-action-deprecated
 * Sometimes I think it makes more sense worry about the future in the future.
 * This is purely a pragmatic approach to programming.
 * Of course, you can always try to make sure that your code doesn't use any deprecated features.
 * On the other hand, deprecated features usually stay around for a long time and it may be that your app will be end-of-lifed before the deprecated features go away.
 * Android N is so new that I wouldn't spend a lot of time worrying about it. Yet. Just my 2 cents.
 * Please note that I wrote a comment to the question and didn't suggest that "don't do that" was a valid answer.
 * – David Wasser Apr 6 '16 at 10:03
 */
@Suppress("DEPRECATION")
class NetworkStateObservable(private var context: Context?) : NaviLiveData<Boolean>(), LifecycleObserver {
    private val networkStateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION != intent.action) return
            val isConnected = SystemUtil.hasInternetConnection(context)
            if (value == null || value != isConnected) {
                setValue(isConnected)
            }
        }
    }

    init {
        context?.let {
            if (!SystemUtil.hasInternetConnection(it)) {
                setValue(false)
            }
        }

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun startToObserveNetwork() {
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context?.registerReceiver(networkStateReceiver, intentFilter)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun stopToObserveNetwork() {
        context?.unregisterReceiver(networkStateReceiver)
        context = null
    }

}
