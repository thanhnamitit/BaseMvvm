package com.vti.base.view.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import com.vti.base.extension.koin.BaseKoinFragment
import com.vti.base.message.MessageManager
import com.vti.base.view.component.decorator.MvvmComponent
import com.vti.base.view.component.decorator.MvvmComponentDecorator
import com.vti.base.viewmodel.BaseViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.core.inject

abstract class BaseMvvmFragment<BINDING : ViewDataBinding, VM : BaseViewModel> : BaseKoinFragment(), MvvmComponent<BINDING, VM> {
    val messageManager: MessageManager by inject()

    override lateinit var binding: BINDING
    override val viewModel: VM by lazy {
        getViewModel(getViewModelType())
    }
    private val fragmentDecorator by lazy { MvvmComponentDecorator(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentDecorator.setupViewModel(lifecycle)
        arguments?.let {
            handleArguments(it)
        }
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
