package com.navanvine.app.base

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.NavOptions
import androidx.navigation.Navigation.findNavController
import com.vti.base.mvvm.view.BaseMvvmFragment
import com.vti.base.mvvm.viewmodel.BaseViewModel
import com.navanvine.app.R
import com.navanvine.app.screen.MainActivity

abstract class BaseFragment<BINDING : ViewDataBinding, VM : BaseViewModel> : BaseMvvmFragment<BINDING, VM>() {
    val mainViewModel by lazy { (activity as MainActivity).viewModel }
    override fun getLayoutVariableId(): Int {
        return BR.viewModel
    }

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
