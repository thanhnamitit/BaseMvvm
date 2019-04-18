package com.navanvine.app.base

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import com.vti.base.mvvm.view.BaseMvvmActivity
import com.vti.base.mvvm.viewmodel.BaseViewModel

abstract class BaseActivity<B : ViewDataBinding, M : BaseViewModel> : BaseMvvmActivity<B, M>() {
    override fun getLayoutVariableId(): Int {
        return BR.viewModel
    }
}
