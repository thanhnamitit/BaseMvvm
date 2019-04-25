package com.vti.base.view.dialog.base

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatDialog
import androidx.core.content.res.ResourcesCompat
import com.vti.base.R

open class BaseDialog(context: Context) : AppCompatDialog(context, R.style.Theme_Dialog) {

    init {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getWindow() != null) {
            // Spread the dialog as large as the screen.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                getWindow()!!.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow()!!.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            }
            getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
    }

    override fun setContentView(view: View) {
        super.setContentView(wrap(view))
    }

    @SuppressLint("ClickableViewAccessibility")
    open fun wrap(content: View): View {
        val resources = getContext().getResources()
        val verticalMargin = resources.getDimensionPixelOffset(R.dimen.dialog_vertical_margin)
        var horizontalMargin = resources.getDimensionPixelOffset(R.dimen.dialog_horizontal_margin)


        val frameLayout = FrameLayout(getContext())
        val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(horizontalMargin, verticalMargin, horizontalMargin, verticalMargin)
        layoutParams.gravity = Gravity.CENTER
        frameLayout.addView(content, layoutParams)
        frameLayout.background = ColorDrawable(ResourcesCompat.getColor(resources, R.color.scrim, getContext().getTheme()))
        return frameLayout
    }
}
