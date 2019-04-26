package com.navanvine.app.extension.function

import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation.findNavController
import com.navanvine.app.R

fun Fragment.getNavController(id: Int) {
    findNavController(requireActivity(), id).navigate(id)
}

fun Fragment.navigate(id: Int) {
    val options = NavOptions.Builder().setEnterAnim(R.anim.slide_in_right).setExitAnim(R.anim.slide_out_left).setPopEnterAnim(R.anim.slide_in_left).setPopExitAnim(R.anim.slide_out_right).build()
    findNavController(requireActivity(), R.id.fragment_navigation).navigate(id)
}