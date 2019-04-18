package com.vti.base.util

import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager

object SystemUtil {
    fun hasInternetConnection(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }

    fun isGpsEnable(context: Context): Boolean {
        return (context.getSystemService(Context.LOCATION_SERVICE) as LocationManager).let {
            it.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || it.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }
    }
}