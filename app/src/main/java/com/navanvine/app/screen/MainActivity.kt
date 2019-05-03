package com.navanvine.app.screen

import com.navanvine.app.BR
import com.navanvine.app.R
import com.navanvine.app.databinding.ActivityMainBinding
import com.vti.location.component.LocationActivity
import kotlin.reflect.KClass


class MainActivity : LocationActivity<ActivityMainBinding, MainViewModel>() {
    override fun getLayoutVariableId(): Int {
        return BR.viewModel
    }

    override fun getViewModelType(): KClass<MainViewModel> {
        return MainViewModel::class
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onViewReady() {
        super.onViewReady()
        setupWithMessageManager()
    }
}
