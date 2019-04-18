package com.vti.location

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.vti.location.component.GeoLocationService

abstract class LocationServiceConnection(
    val context: Context, val serviceClass: Class<out GeoLocationService> = GeoLocationService::class.java
) : ServiceConnection {

    var isBound = false
    fun getIntent() = Intent(context, serviceClass)

    fun connect() {
        if (isBound) return
        val intent = getIntent()
        context.startService(intent)
        context.bindService(intent, this, Context.BIND_AUTO_CREATE)
    }


    fun disconnect(isStopService: Boolean) {
        if (!isBound) {
            return
        }
        context.unbindService(this)
        if (isStopService) {
            context.stopService(getIntent())
        }
        isBound = false
    }


    override fun onServiceDisconnected(name: ComponentName?) {
        isBound = false
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder) {
        isBound = true
        if (service is GeoLocationService.ServiceBinder == false) return
        onServiceBounded(service.service)
    }

    abstract fun onServiceBounded(service: GeoLocationService)

}