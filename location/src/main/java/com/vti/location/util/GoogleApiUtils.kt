package com.vti.location.util

import android.app.Activity
import android.content.Context
import android.location.LocationManager
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import timber.log.Timber

/**
 * Created by VTI Android Team on 3/23/2018.
 * Copyright © 2018 VTI Inc. All rights reserved.
 */
object GoogleApiUtils {

    fun isGooglePlayServiceAvailable(context: Context): Boolean {
        val resCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)
        Timber.d("isGooglePlayServiceAvailable %s", resCode)
        return resCode == ConnectionResult.SUCCESS
    }

    fun isGpsEnable(context: Context): Boolean {
        val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun makeGoogleApiServiceAvailable(
        activity: Activity, completeListener: OnCompleteListener<Void>?, failureListener: OnFailureListener?
    ) {
        val task = GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(activity)
        if (completeListener != null) {
            task.addOnCompleteListener(completeListener)
        }

        if (failureListener != null) {
            task.addOnFailureListener(failureListener)
        }
    }

    fun boundFromPoints(points: List<LatLng>): LatLngBounds {
        val builder = LatLngBounds.Builder()
        for (latLng in points) {
            builder.include(latLng)
        }
        return builder.build()
    }

    fun bearing(origin: LatLng, destination: LatLng): Float {
        val PI = Math.PI
        val orgLat = origin.latitude * PI / 180
        val orgLng = origin.longitude * PI / 180
        val desLat = destination.latitude * PI / 180
        val ostLng = destination.longitude * PI / 180
        val deltaLng = ostLng - orgLng
        val y = Math.sin(deltaLng) * Math.cos(desLat)
        val x = Math.cos(orgLat) * Math.sin(desLat) - (Math.sin(orgLat) * Math.cos(desLat) * Math.cos(deltaLng))
        var brng = Math.atan2(y, x)
        brng = Math.toDegrees(brng)
        brng = (brng + 360) % 360
        return brng.toFloat()
    }

}
