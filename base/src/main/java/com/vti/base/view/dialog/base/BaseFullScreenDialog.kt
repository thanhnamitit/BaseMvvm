package com.vti.base.view.dialog.base

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout

class BaseFullScreenDialog(context: Context) : BaseDialog(context) {
    override fun wrap(content: View): View {
        val frameLayout = FrameLayout(context)
        val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        layoutParams.gravity = Gravity.CENTER
        frameLayout.addView(content, layoutParams)
        return frameLayout
    }
}