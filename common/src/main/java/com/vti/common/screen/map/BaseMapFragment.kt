package com.vti.common.screen.map

import android.location.Location
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.vti.base.view.component.BaseMvvmFragment
import com.vti.common.BR
import com.vti.common.R
import com.vti.common.databinding.FragmentMapBinding
import com.vti.location.extension.`fun`.moveTo
import com.vti.location.extension.`fun`.moveToSplashPosition
import com.vti.common.screen.BaseMainViewModel

abstract class BaseMapFragment<T : BaseMapViewModel> : BaseMvvmFragment<FragmentMapBinding, T>(), OnMapReadyCallback {
    lateinit var googleMap: GoogleMap

    override fun getLayoutId(): Int {
        return R.layout.fragment_map
    }

    abstract fun requireMainViewModel(): BaseMainViewModel

    override fun getLayoutVariableId(): Int {
        return BR.viewModel
    }

    override fun onViewReady() {
        super.onViewReady()
        setupGoogleMap()
    }

    fun setupGoogleMap() {
        val supportMapFragment = childFragmentManager.findFragmentById(R.id.map_container) as SupportMapFragment
        supportMapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.let {
            googleMap.moveToSplashPosition()
            initGoogleMap(googleMap)
            onEverythingReady(googleMap)
        }
    }

    fun initGoogleMap(googleMap: GoogleMap) {
        this.googleMap = googleMap
        requireMainViewModel().location.observeOnce(this, { googleMap.moveTo(it) })
    }

    fun moveToPosition(location: Location) {
        googleMap.moveTo(location)
    }

    abstract fun onEverythingReady(googleMap: GoogleMap)

}