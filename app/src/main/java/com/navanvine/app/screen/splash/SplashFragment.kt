package com.navanvine.app.screen.splash

import android.os.Handler
import com.navanvine.app.R
import com.navanvine.app.base.BaseFragment
import com.navanvine.app.databinding.FragmentSplashBinding
import kotlin.reflect.KClass

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>() {
    override fun getViewModelType(): KClass<SplashViewModel> = SplashViewModel::class

    override fun getLayoutId() = R.layout.fragment_splash

    companion object {
        const val DELAY_TIME = 100L
    }

    override fun onViewReady() {
        super.onViewReady()
        Handler().postDelayed({
            navigate(R.id.action_splashFragment_to_fakeSmsFragment)
        }, DELAY_TIME)
    }
}