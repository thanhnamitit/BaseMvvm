package com.navanvine.app.base

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import com.vti.base.view.component.BaseMvvmActivity
import com.vti.base.viewmodel.BaseViewModel

abstract class BaseActivity<B : ViewDataBinding, M : BaseViewModel> : BaseMvvmActivity<B, M>() {
    override fun getLayoutVariableId(): Int {
        return BR.viewModel
    }
}
