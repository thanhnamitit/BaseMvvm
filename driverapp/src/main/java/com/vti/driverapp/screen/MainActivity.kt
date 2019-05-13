package com.vti.driverapp.screen

import android.os.Handler
import androidx.lifecycle.Observer
import com.vti.common.screen.BaseMainActivity
import com.vti.common.screen.map.BaseMapFragment
import com.vti.driverapp.screen.map.MapFragment
import timber.log.Timber
import kotlin.reflect.KClass

class MainActivity : BaseMainActivity<MainViewModel>() {
    override fun onViewReady() {
        super.onViewReady()
        setupWithMessageManager()
        Handler().postDelayed({ startDetectLocation() }, 3000)
    }

    override fun getViewModelType(): KClass<MainViewModel> {
        return MainViewModel::class
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel.location.observe(this, Observer {
            Timber.i(it.latitude.toString() + " " + it.longitude.toString())
        })
    }

    override fun getMapFragment(): BaseMapFragment<*> {
        return MapFragment()
    }

}
