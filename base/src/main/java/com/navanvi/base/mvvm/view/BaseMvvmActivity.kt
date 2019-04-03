package com.navanvi.base.mvvm.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.navanvi.base.di.view.BaseDaggerActivity
import com.navanvi.base.extension.livedata.EventObserver
import com.navanvi.base.mvvm.viewmodel.BaseViewModel
import javax.inject.Inject

abstract class BaseMvvmActivity<BINDING : ViewDataBinding, VM : BaseViewModel> : BaseDaggerActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    abstract val viewModelType: Class<VM>
    abstract val layoutId: Int

    val binding by lazy { DataBindingUtil.setContentView(this, layoutId) as BINDING }
    val viewModel: VM by lazy {
        ViewModelProviders.of(this@BaseMvvmActivity, viewModelFactory).get(viewModelType)
    }
    open val variableId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doAtFirst()
        setupViewModel()
        viewModel.eventNavigator.observe(this, EventObserver { onReceiveEvent(it) })
        setupViewDataBinding()
        binding.setVariable(variableId, viewModel)
        binding.executePendingBindings()
        setContentView(binding.root)
        onViewReady()
    }


    open fun doAtFirst() {}
    open fun onViewReady() {}
    open fun onReceiveEvent(event: Int) {}
    open fun setupViewDataBinding() {}
    open fun setupViewModel() {}


}
