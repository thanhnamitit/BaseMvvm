package com.vti.base.navigation

import android.content.Context
import android.view.LayoutInflater
import com.vti.base.databinding.ToolbarBinding
import com.vti.base.navigation.excutor.NavigationImpl

class DefaultNavigationBuilder : NavigationBuilder<DefaultNavigationBuilder>() {
    override fun getMySelf(): DefaultNavigationBuilder {
        return this
    }

    override fun build(context: Context): Navigation {
        return NavigationImpl(this, buildToolbar(context))
    }

    fun buildToolbar(context: Context): ToolbarBinding {
        return ToolbarBinding.inflate(LayoutInflater.from(context))
    }

}