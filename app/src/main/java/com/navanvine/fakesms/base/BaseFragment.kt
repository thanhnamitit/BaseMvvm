package com.navanvine.fakesms.base

import androidx.databinding.ViewDataBinding
import androidx.navigation.NavOptions
import androidx.navigation.Navigation.findNavController
import com.navanvi.base.mvvm.view.BaseMvvmFragment
import com.navanvi.base.mvvm.viewmodel.BaseViewModel
import com.navanvine.fakesms.BR
import com.navanvine.fakesms.R
import com.navanvine.fakesms.screen.MainActivity

abstract class BaseFragment<BINDING : ViewDataBinding, VM : BaseViewModel> : BaseMvvmFragment<BINDING, VM>() {
    val mainViewModel by lazy { (activity as MainActivity).viewModel }
    override val variableId: Int
        get() = BR.viewModel

    fun navigate(id: Int) {
        val options = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .build()
        findNavController(requireActivity(), R.id.fragment_navigation).navigate(id)
    }
}
