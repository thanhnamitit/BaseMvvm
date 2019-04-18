package com.vti.base.mvvm.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.vti.base.extension.dagger.BaseKoinActivity
import com.vti.base.extension.livedata.event.EventObserver
import com.vti.base.mvvm.SimpleLifecycleOwnerProvider
import com.vti.base.mvvm.viewmodel.BaseViewModel
import com.vti.base.util.observable.NetworkStatusObservable
import org.koin.android.viewmodel.ext.android.getViewModel
import kotlin.reflect.KClass

abstract class BaseMvvmActivity<BINDING : ViewDataBinding, VM : BaseViewModel> : BaseKoinActivity(),
    SimpleLifecycleOwnerProvider {

    abstract fun getViewModelType(): KClass<VM>
    abstract fun getLayoutId(): Int
    abstract fun getLayoutVariableId(): Int

    val binding by lazy { DataBindingUtil.setContentView(this, getLayoutId()) as BINDING }
    val viewModel: VM by lazy { getViewModel(getViewModelType()) }

    val isForeground
        get() = viewModel.isForeground


    private val networkStateObservable by lazy { NetworkStatusObservable(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(parseBundle(savedInstanceState))
        setupFirst()
        setupFeature()
        setupViewModel()
        setupViewDataBinding()
        onViewReady()
    }

    protected open fun setupFeature() {
        if (isNeedToObserveToNetworkState()) {
            observeToNetworkState()
        }
    }

    open fun parseBundle(savedInstanceState: Bundle?) = savedInstanceState
    open fun setupFirst() {}
    open fun onViewReady() {}
    open fun onReceiveEvent(event: Int) {}
    open fun setupViewModel() {
        if (viewModel.hasNotSetupYet()) {
            viewModel.normalEventNavigator.observe(this, EventObserver { onReceiveEvent(it) })
            viewModel.expressEventNavigator.observeUntilDestroyed(this, EventObserver { onReceiveEvent(it) })
        }
        lifecycle.addObserver(viewModel)
    }

    open fun setupObserver() {

    }

    open fun setupViewDataBinding() {
        binding.setVariable(getLayoutVariableId(), viewModel)
        binding.executePendingBindings()
        binding.lifecycleOwner = this
    }

    open fun isNeedToObserveToNetworkState() = false

    fun observeToNetworkState() {
        lifecycle.addObserver(networkStateObservable)
        networkStateObservable.observe(this, Observer {
            if (it) onNetworkReconnected()
            else onNetworkDisconnected()
        })
    }

    open fun onNetworkReconnected() {}
    open fun onNetworkDisconnected() {}

    override fun getSimpleLifecycleOwner(): LifecycleOwner = viewModel

    override fun getNormalLifecycleOwner(): LifecycleOwner = this

}
