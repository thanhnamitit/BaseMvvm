package com.navanvine.fakesms.screen.splash

import android.os.Handler
import com.navanvine.fakesms.R
import com.navanvine.fakesms.base.BaseFragment
import com.navanvine.fakesms.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>() {
    companion object {
        const val DELAY_TIME = 100L
    }

    override val viewModelType: Class<SplashViewModel>
        get() = SplashViewModel::class.java
    override val layoutId: Int
        get() = R.layout.fragment_splash

    override fun onViewReady() {
        super.onViewReady()
        Handler().postDelayed({
            navigate(R.id.action_splashFragment_to_fakeSmsFragment)
        }, DELAY_TIME)
    }
}