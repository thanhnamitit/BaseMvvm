package com.vti.base.mvvm.decorator

import androidx.databinding.ViewDataBinding
import com.vti.base.mvvm.SimpleLifecycleOwnerProvider
import com.vti.base.mvvm.viewmodel.BaseViewModel

interface MvvmComponent<BINDING : ViewDataBinding, VM : BaseViewModel> : SimpleLifecycleOwnerProvider {
    val viewModel: VM
    var binding: BINDING
    fun getViewModelType(): Class<VM>
    fun getLayoutId(): Int
    fun getLayoutVariableId(): Int
    fun onViewReady() {}
    fun onReceiveEvent(event: Int) {}
    fun setupObserver() {}
}
