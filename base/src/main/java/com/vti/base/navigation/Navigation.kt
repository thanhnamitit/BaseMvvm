package com.vti.base.navigation

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import com.vti.base.extension.livedata.NaviLiveData

interface Navigation {
    val navigationBackgroundColor: NaviLiveData<Int>
    fun updateTitle(title: String)
    fun updateTitle(titleId: Int)
    fun updateBackIcon(iconId: Int)
    fun updateTitleTextColor(colorId: Int)
    fun updateBackgroudColor(colorId: Int) {
        navigationBackgroundColor.setValue(colorId)
    }

    fun setupWith(view: View): View

    fun findActivityByContext(context: Context?): Activity? {
        if (context == null) return null
        else if (context is Activity) return context
        else if (context is ContextWrapper) return findActivityByContext((context).baseContext)
        return null
    }
}