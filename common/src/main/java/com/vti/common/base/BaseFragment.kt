package com.vti.common.base

import androidx.databinding.ViewDataBinding
import com.vti.base.view.component.BaseMvvmFragment
import com.vti.base.viewmodel.BaseViewModel
import com.vti.common.BR

abstract class BaseFragment<Binding : ViewDataBinding, ViewModel : BaseViewModel> : BaseMvvmFragment<Binding, ViewModel>() {


    override fun getLayoutVariableId(): Int {
        return BR.viewModel
    }
}