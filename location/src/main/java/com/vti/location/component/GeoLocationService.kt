package com.vti.location.component

import android.Manifest
import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Binder
import android.os.Bundle
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.vti.location.data.model.LocationWrapper
import com.vti.location.extension.LocationProvider
import com.vti.location.util.GoogleApiUtils
import timber.log.Timber

class GeoLocationService : Service(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    companion object {
        private const val NOTIFICATION_ID = 6626
        private const val FASTEST_INTERVAL = 5000L //2 sec
        private const val DEFAULT_INTERVAL = 10000L //5 secs

        const val ERROR_CONNECTION_FAILED = 0
        const val ERROR_PERMISSION_SUSPENDED = 1
        const val ERROR_LOCATION_DISABLED = 2
        const val ERROR_SERVICE_VERSION_UPDATE_REQUIRED = 3
        const val STATUS_LOCATION_AVAILABLE = 4
    }

    interface State {
        companion object {
            val INIT = 0
            val CONNECTING = 1
            val CONNECTED = 2
            val CONNECTION_FAIL = 3
            val CONNECTION_SUSPENDED = 4
            val UNDEFINED = -1
            val PERMISSION_ERROR = -2
            val GPS_DISABLE = 5
        }
    }

    inner class ServiceBinder : Binder() {
        val service: GeoLocationService
            get() = this@GeoLocationService
    }

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationProvider.setValue(LocationWrapper.fromLocation(locationResult.lastLocation))
        }
    }

    val locationProvider = LocationProvider(this::onLocationListenStateChanged)
    private var googleApiClient: GoogleApiClient? = null
    private var locationProviderClient: FusedLocationProviderClient? = null
    private lateinit var locationRequest: LocationRequest

    var state = State.UNDEFINED


    override fun onCreate() {
        super.onCreate()
        if (!GoogleApiUtils.isGooglePlayServiceAvailable(this)) {
            state = State.CONNECTION_SUSPENDED
            return
        }
        state = State.INIT
        setupLocationRequest()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        tryToReconnect()
        return Service.START_STICKY
    }

    override fun onBind(intent: Intent?) = ServiceBinder()

    override fun onConnected(bundle: Bundle?) {
        state = State.CONNECTED
        locationProvider.setValue(LocationWrapper.fromStatus(STATUS_LOCATION_AVAILABLE))
        getLastKnowLocationForLocationProvider()
        startObserveLocation()
    }

    @SuppressLint("MissingPermission")
    fun getLastKnowLocationForLocationProvider() {
        locationProviderClient?.getLastLocation()?.addOnCompleteListener { result ->
            result.result?.let {
                locationProvider.location = it
            }
        }

    }

    override fun onConnectionSuspended(i: Int) {
        googleApiClient = null
        state = State.CONNECTION_SUSPENDED
        locationProvider.setValue(LocationWrapper.fromStatus(ERROR_CONNECTION_FAILED))
        Timber.e("Google api client connection suspended %d", i)
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        state = State.CONNECTION_FAIL
        if (connectionResult.hasResolution()) {
            locationProvider.setValue(LocationWrapper.fromConnectionResult(connectionResult))
        } else {
            Timber.e(
                "Google api client connection failed, no resolution cause:%d %s",
                connectionResult.errorCode,
                connectionResult.errorMessage
            )
            if (connectionResult.errorCode == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED) {
                locationProvider.setValue(LocationWrapper.fromStatus(ERROR_SERVICE_VERSION_UPDATE_REQUIRED))
            } else {
                locationProvider.setValue(LocationWrapper.fromStatus(ERROR_SERVICE_VERSION_UPDATE_REQUIRED))
            }
        }
    }

    private fun setupLocationRequest() {
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest().apply {
            fastestInterval = FASTEST_INTERVAL
            interval = DEFAULT_INTERVAL
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    fun onLocationListenStateChanged(hasObserver: Boolean) {
        if (hasObserver) {
            if (state == State.CONNECTED) startObserveLocation()
            else tryToReconnect()
        } else {
            stopObserveLocation()
        }
    }

    fun tryToReconnect() {
        if (state == State.GPS_DISABLE) {
            state = State.CONNECTED
            startObserveLocation()
            return
        }
        if (state != State.CONNECTING && state != State.CONNECTED) {
            setupLocationClient()
        }
    }

    private fun setupLocationClient() {
        if (googleApiClient == null) {
            googleApiClient =
                GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API).build()
        }
        state = State.CONNECTING
        googleApiClient?.connect()
    }

    private fun startObserveLocation() {
        if (state != State.CONNECTED) {
            return
        }
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            state = State.PERMISSION_ERROR
            locationProvider.setValue(LocationWrapper.fromStatus(ERROR_PERMISSION_SUSPENDED))
            return
        }
        if (!GoogleApiUtils.isGpsEnable(this)) {
            state = State.GPS_DISABLE
            locationProvider.setValue(LocationWrapper.fromStatus(ERROR_LOCATION_DISABLED))
            return
        }

        if (locationProviderClient == null) {
            setupLocationRequest()
        }

        locationProviderClient?.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    private fun stopObserveLocation() {
        locationProviderClient?.removeLocationUpdates(locationCallback)
    }

    fun getLocationRequest(): LocationRequest {
        return locationRequest
    }
}