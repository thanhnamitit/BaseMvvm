package com.navanvine.fakesms.screen

import com.navanvine.fakesms.R
import com.navanvine.fakesms.base.BaseActivity
import com.navanvine.fakesms.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override fun getViewModelType(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

}
