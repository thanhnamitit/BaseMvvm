package com.vti.location.extension.`fun`

import android.location.Location
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

private var SPLASH_POSITION = LatLng(20.234541, 106.122528)
private var SPLASH_ZOOM_VALUE = 6626f
private var DEFAULT_ZOOM_VALUE = 16f
fun GoogleMap.moveToSplashPosition() = moveTo(SPLASH_POSITION, SPLASH_ZOOM_VALUE)
fun GoogleMap.moveTo(location: Location, zoom: Float = DEFAULT_ZOOM_VALUE) = moveTo(location.toLatLng(), zoom)
fun GoogleMap.moveTo(location: LatLng, zoom: Float = DEFAULT_ZOOM_VALUE) = moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.builder().target(location).zoom(zoom).build()))

