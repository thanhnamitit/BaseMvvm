package com.vti.base.extension.databinding

import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter

@BindingAdapter("heightEqualsStatusBar")
fun setHeightEqualsStatusBar(view: View, apply: Boolean) {
    if (apply) {
        val context = view.context
        var statusBarHeight = 0
        val resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId)
        }
        val layoutParam = view.layoutParams
        layoutParam.height = statusBarHeight
        view.layoutParams = layoutParam
    }
}