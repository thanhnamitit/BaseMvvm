package com.navanvine.fakesms.base

import androidx.databinding.ViewDataBinding
import com.vti.base.mvvm.view.BaseMvvmActivity
import com.vti.base.mvvm.viewmodel.BaseViewModel
import com.navanvine.fakesms.BR

abstract class BaseActivity<B : ViewDataBinding, M : BaseViewModel> : BaseMvvmActivity<B, M>() {
    override fun getLayoutVariableId(): Int {
        return BR.viewModel
    }
}
