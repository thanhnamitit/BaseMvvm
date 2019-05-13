package com.vti.base.view.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.vti.base.extension.koin.BaseKoinFragment
import com.vti.base.message.MessageManager
import com.vti.base.navigation.NavigationBuilder
import com.vti.base.navigation.Navigation
import com.vti.base.view.component.decorator.MvvmComponent
import com.vti.base.view.component.decorator.MvvmComponentDecorator
import com.vti.base.viewmodel.BaseViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.getViewModel

abstract class BaseMvvmFragment<BINDING : ViewDataBinding, VM : BaseViewModel> : BaseKoinFragment(), MvvmComponent<BINDING, VM>, OnToolbarColorChangeListener {
    val messageManager: MessageManager by inject()
    var navigation: Navigation? = null
    override lateinit var binding: BINDING
    override val viewModel: VM by lazy {
        generateViewModel()
    }
    private val fragmentDecorator by lazy { MvvmComponentDecorator(this) }

    open protected fun generateViewModel() = getViewModel(getViewModelType())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentDecorator.setupViewModel(lifecycle)
        arguments?.let {
            handleArguments(it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mainContentView = fragmentDecorator.getViewDataBinding(inflater, container).root.let { it }
        return getNavigationBuilder()?.let {
            this.navigation = it.build(requireContext())
            this.navigation?.navigationBackgroundColor?.observe(this, Observer { onToolbarBackgroundColorChange(it) })
            navigation?.run { setupWith(mainContentView) }
        } ?: mainContentView
    }

    open fun getNavigationBuilder(): NavigationBuilder<*>? {
        return null
    }

    override fun onToolbarBackgroundColorChange(resId: Int) {
        (requireActivity() as? OnToolbarColorChangeListener)?.onToolbarBackgroundColorChange(resId)
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
