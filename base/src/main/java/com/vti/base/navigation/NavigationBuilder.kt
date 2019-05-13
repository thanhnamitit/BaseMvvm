package com.vti.base.navigation

import android.content.Context
import com.vti.base.R

abstract class NavigationBuilder<T : NavigationBuilder<T>> internal constructor() : NavigationInformation {
    override var toolbarLayoutId = R.layout.toolbar
    override var backIconId: Int = NavigationInformation.NOT_HANDLE_RESOURCE_ID_VALUE
    override var title: String = ""
    override var titleId: Int = NavigationInformation.NOT_HANDLE_RESOURCE_ID_VALUE
    override var menu: Int = NavigationInformation.NOT_HANDLE_RESOURCE_ID_VALUE
    override var titleTextColor: Int = NavigationInformation.NOT_HANDLE_RESOURCE_ID_VALUE
    override var backgroundColor: Int = NavigationInformation.NOT_HANDLE_RESOURCE_ID_VALUE

    fun toolbarLayoutId(value: Int): T {
        this.toolbarLayoutId = value
        return getMySelf()
    }

    fun navigationIconRes(value: Int): T {
        this.backIconId = value
        return getMySelf()
    }

    fun titleRes(value: Int): T {
        this.titleId = value
        return getMySelf()
    }

    fun title(value: String): T {
        this.title = value
        return getMySelf()
    }

    fun menuRes(value: Int): T {
        this.menu = value
        return getMySelf()
    }

    fun titleTextColor(value: Int): T {
        this.titleTextColor = value
        return getMySelf()
    }

    fun backgroundColor(value: Int): T {
        this.backgroundColor = value
        return getMySelf()
    }

    abstract fun getMySelf(): T
    abstract fun build(context: Context): Navigation

    companion object {
        fun default() = DefaultNavigationBuilder()
    }
}