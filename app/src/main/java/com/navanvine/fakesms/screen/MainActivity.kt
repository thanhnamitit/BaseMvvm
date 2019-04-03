package com.navanvine.fakesms.screen

import com.navanvine.fakesms.R
import com.navanvine.fakesms.base.BaseActivity
import com.navanvine.fakesms.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val layoutId: Int
        get() = R.layout.activity_main
    override val viewModelType: Class<MainViewModel>
        get() = MainViewModel::class.java
}
