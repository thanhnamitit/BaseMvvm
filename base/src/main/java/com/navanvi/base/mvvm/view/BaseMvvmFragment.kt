package com.navanvi.base.mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.navanvi.base.extension.livedata.EventObserver
import com.navanvi.base.mvvm.viewmodel.BaseViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseMvvmFragment<BINDING : ViewDataBinding, VM : BaseViewModel> : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    abstract val viewModelType: Class<VM>
    abstract val layoutId: Int
    lateinit var binding: BINDING
    val viewModel: VM by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(viewModelType)
    }
    open val variableId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        viewModel.eventNavigator.observe(this, EventObserver { onReceiveEvent(it) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        setupViewDataBinding()
        binding.setVariable(variableId, viewModel)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewReady()
    }

    open fun onViewReady() {}
    open fun onReceiveEvent(event: Int) {}
    open fun setupViewDataBinding() {}
    open fun setupViewModel() {}
}
