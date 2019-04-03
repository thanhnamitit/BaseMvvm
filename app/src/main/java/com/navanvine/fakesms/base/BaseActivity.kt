package com.navanvine.fakesms.base

import android.view.WindowManager
import androidx.databinding.ViewDataBinding
import com.navanvi.base.mvvm.view.BaseMvvmActivity
import com.navanvi.base.mvvm.viewmodel.BaseViewModel
import com.navanvine.fakesms.BR

abstract class BaseActivity<B : ViewDataBinding, M : BaseViewModel> : BaseMvvmActivity<B, M>() {
    override val variableId: Int
        get() = BR.viewModel

    override fun doAtFirst() {
        super.doAtFirst()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
}
