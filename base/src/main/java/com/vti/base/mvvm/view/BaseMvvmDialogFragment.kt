package com.vti.base.mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.vti.base.extension.dagger.BaseDaggerDialogFragment
import com.vti.base.mvvm.decorator.MvvmComponentDecorator
import com.vti.base.mvvm.decorator.MvvmComponent
import com.vti.base.mvvm.viewmodel.BaseViewModel
import javax.inject.Inject

abstract class BaseMvvmDialogFragment<BINDING : ViewDataBinding, VM : BaseViewModel> : BaseDaggerDialogFragment(),
    MvvmComponent<BINDING, VM> {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    override lateinit var binding: BINDING
    override val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(getViewModelType())
    }
    private val fragmentDecorator by lazy { MvvmComponentDecorator(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentDecorator.setupViewModel()
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