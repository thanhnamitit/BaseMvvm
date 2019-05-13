package com.vti.location.component

import android.Manifest
import android.content.Intent
import android.content.IntentSender
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.vti.base.message.MessageFactory
import com.vti.base.util.hasSelfPermissions
import com.vti.base.util.observable.GpsStatusObservable
import com.vti.base.view.component.BaseMvvmActivity
import com.vti.base.view.dialog.permission.OnPermissionRequestListener
import com.vti.location.LocationServiceConnection
import com.vti.location.data.model.LocationWrapper
import timber.log.Timber

abstract class LocationActivity<BINDING : ViewDataBinding, VM : LocationViewModel> : BaseMvvmActivity<BINDING, VM>(), OnPermissionRequestListener {

    private val REQUEST_CODE_RESOLVE = 0xf01
    private val REQUEST_LOCATION_SETTING = 0xf02
    private val PERMISSION_CONNECT_LOCATION_SERVICE = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)

    lateinit var gpsStatusObservable: GpsStatusObservable
    var locationService: GeoLocationService? = null
    val connection: LocationServiceConnection by lazy {
        object : LocationServiceConnection(this) {
            override fun onServiceBounded(service: GeoLocationService) {
                this@LocationActivity.locationService = service
                service.locationProvider.observe(this@LocationActivity, Observer { onReceiveLocationWrapper(it) })
            }
        }
    }

    override fun setupFeature() {
        super.setupFeature()
        gpsStatusObservable = GpsStatusObservable(this)
        lifecycle.addObserver(gpsStatusObservable)
    }


    override fun setupViewModel() {
        super.setupViewModel()
        viewModel.locationObserveFlag.observe(this, Observer<Boolean> { isTurningOn ->
            if (isTurningOn) startListeningToLocation()
            else stopListeningToLocation()
        })
    }

    fun hasEnoughPermission() = hasSelfPermissions(this, PERMISSION_CONNECT_LOCATION_SERVICE)

    fun startDetectLocation() {
        viewModel.startListeningToLocation()
    }

    fun stopDetectLocation() {
        viewModel.stopListeningToLocation()
    }

    private fun startListeningToLocation() {
        if (!hasEnoughPermission()) {
            messageManager.addMessage(MessageFactory.locationPermissionRequest())
        } else {
            connectToLocationService()
            gpsStatusObservable.start()
            gpsStatusObservable.reObserve(this, Observer { isEnable ->
                if (!isEnable) requestToTurnOnGPS()
            })
        }

    }

    private fun stopListeningToLocation() {
        connection.disconnect(shouldStopServiceWhenDestroy())
        gpsStatusObservable.removeObservers(this)
        gpsStatusObservable.stop()
    }

    override fun onRequestSuccess(permission: Array<String>) {
        startListeningToLocation()
    }

    fun requestToTurnOnGPS() {
        locationService?.let { locationService ->
            val builder = LocationSettingsRequest.Builder()
            val request = locationService.getLocationRequest()
            builder.addLocationRequest(request).setAlwaysShow(true)
            val responseTask = LocationServices.getSettingsClient(this).checkLocationSettings(builder.build())
            responseTask.addOnCompleteListener {}
            responseTask.addOnCanceledListener {}
            responseTask.addOnFailureListener {}
            responseTask.addOnCompleteListener { task ->
                try {
                    val response = task.getResult(ApiException::class.java)
                    Timber.d("Request location setting success %s", response)
                    locationService.tryToReconnect()
                } catch (exception: ApiException) {
                    when (exception.statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                            val resolvable = exception as ResolvableApiException
                            resolvable.startResolutionForResult(this@LocationActivity, REQUEST_LOCATION_SETTING)
                        } catch (e: IntentSender.SendIntentException) {
                            Timber.e(e, "Request location setting %s", e.message)
                        } catch (e: ClassCastException) {
                            Timber.e(e, "Request location setting %s", e.message)
                        }

                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (shouldStopServiceWhenDestroy()) {
            stopListeningToLocation()
        }
    }

    protected fun shouldStopServiceWhenDestroy(): Boolean {
        return true
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.isListeningToLocation()) {
            startListeningToLocation()
        }
    }

    private fun connectToLocationService() {
        if (connection.isBound) {
            return
        }
        connection.connect()
    }

    private fun getServiceClass(): Class<out GeoLocationService> {
        return GeoLocationService::class.java
    }

    fun onReceiveLocationWrapper(locationWrapper: LocationWrapper) {
        when (locationWrapper.status) {
            LocationWrapper.STATUS_OK -> locationWrapper.location?.let { viewModel.location.setValue(it) }
            GeoLocationService.ERROR_LOCATION_DISABLED -> requestToTurnOnGPS()
            GeoLocationService.STATUS_LOCATION_AVAILABLE -> viewModel.locationAvailable.setValue(true)
            GeoLocationService.ERROR_SERVICE_VERSION_UPDATE_REQUIRED -> {
            }
            else -> locationWrapper.connectionResult?.let {
                try {
                    it.startResolutionForResult(this@LocationActivity, REQUEST_CODE_RESOLVE)
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace()
                    Timber.e(e, "Request resolve get exception %s", e.message)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_RESOLVE) {
            if (resultCode == RESULT_OK) {
                locationService?.tryToReconnect()
            } else {
                Timber.e("Cannot resolve google api authentication")
            }
        } else if (requestCode == REQUEST_LOCATION_SETTING) {
            if (resultCode == RESULT_OK) {
                locationService?.tryToReconnect()
            } else {
                Timber.e("Request location setting failed, user not allow")
            }
        }
    }


}