package com.vti.base.mvvm.decorator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import com.vti.base.extension.livedata.event.EventObserver
import com.vti.base.mvvm.viewmodel.BaseViewModel

class MvvmComponentDecorator<BINDING : ViewDataBinding, VM : BaseViewModel>(val fragment: MvvmComponent<BINDING, VM>) {

    fun setupViewModel(lifecycle: Lifecycle) {
        val viewModel = fragment.viewModel
        if (!viewModel.hasNotSetupYet()) {
            viewModel.normalEventNavigator.observe(
                fragment.getNormalLifecycleOwner(),
                EventObserver { fragment::onReceiveEvent })
            viewModel.normalEventNavigator.observeUntilDestroyed(fragment, EventObserver { fragment::onReceiveEvent })
            fragment.setupObserver()
        }
        lifecycle.addObserver(viewModel)
    }

    fun getViewDataBinding(inflater: LayoutInflater, container: ViewGroup?): BINDING {
        val binding: BINDING = DataBindingUtil.inflate(inflater, fragment.getLayoutId(), container, false)
        fragment.binding = binding
        setupViewDataBinding()
        return binding
    }

    fun setupViewDataBinding() {
        val binding = fragment.binding;
        binding.setVariable(fragment.getLayoutVariableId(), fragment.viewModel)
        binding.executePendingBindings()
        binding.lifecycleOwner = fragment.getNormalLifecycleOwner()
    }

    fun onViewCreated() {
        fragment.onViewReady()
    }
}