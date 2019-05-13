package com.vti.base.navigation

interface NavigationInformation {
    companion object {
        const val NOT_HANDLE_RESOURCE_ID_VALUE = -1
    }

    var toolbarLayoutId: Int
    var backIconId: Int
    var title: String
    var titleId: Int
    var menu: Int
    var titleTextColor: Int
    var backgroundColor: Int
}