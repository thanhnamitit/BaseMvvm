package com.vti.common.screen

import com.vti.common.BR
import com.vti.common.R
import com.vti.common.databinding.ActivityMainBinding
import com.vti.common.screen.map.BaseMapFragment
import com.vti.location.component.LocationActivity

abstract class BaseMainActivity<T : BaseMainViewModel> : LocationActivity<ActivityMainBinding, T>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getLayoutVariableId(): Int {
        return BR.viewModel
    }

    override fun onViewReady() {
        super.onViewReady()
        addMapFragment()
    }

    abstract fun getMapFragment(): BaseMapFragment<*>

    fun addMapFragment() = supportFragmentManager.beginTransaction().replace(R.id.frame_map, getMapFragment()).commit()

}
