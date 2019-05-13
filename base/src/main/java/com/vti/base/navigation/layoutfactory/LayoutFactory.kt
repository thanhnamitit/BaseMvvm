package com.vti.base.navigation.layoutfactory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

interface LayoutFactory {
    fun produceLayout(inflater: LayoutInflater, container: ViewGroup): View
}