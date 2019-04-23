package com.vti.base.mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import com.vti.base.extension.koin.BaseKoinDialogFragment
import com.vti.base.mvvm.decorator.MvvmComponent
import com.vti.base.mvvm.decorator.MvvmComponentDecorator
import com.vti.base.mvvm.viewmodel.BaseViewModel
import org.koin.android.viewmodel.ext.android.getViewModel

abstract class BaseMvvmDialogFragment<BINDING : ViewDataBinding, VM : BaseViewModel> : BaseKoinDialogFragment(),
    MvvmComponent<BINDING, VM> {

    override lateinit var binding: BINDING
    override val viewModel: VM by lazy {
        getViewModel(getViewModelType())
    }
    private val fragmentDecorator by lazy { MvvmComponentDecorator(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentDecorator.setupViewModel(lifecycle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return fragmentDecorator.getViewDataBinding(inflater, container).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentDecorator.onViewCreated()
    }

    override fun getSimpleLifecycleOwner(): LifecycleOwner {
        return viewModel
    }

    override fun getNormalLifecycleOwner(): LifecycleOwner {
        return this
    }
}