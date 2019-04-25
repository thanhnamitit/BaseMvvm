package com.vti.base.view.component.decorator

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.vti.base.provider.SimpleLifecycleOwnerProvider
import com.vti.base.viewmodel.BaseViewModel
import kotlin.reflect.KClass

interface MvvmComponent<BINDING : ViewDataBinding, VM : BaseViewModel> : SimpleLifecycleOwnerProvider {

    val viewModel: VM
    var binding: BINDING
    fun getViewModelType(): KClass<VM>
    fun getLayoutId(): Int
    fun getLayoutVariableId(): Int
    fun onViewReady() {}
    fun onReceiveEvent(event: Int) {}
    fun setupViewModel() {}
    fun handleArguments(arguments: Bundle) {}
}
