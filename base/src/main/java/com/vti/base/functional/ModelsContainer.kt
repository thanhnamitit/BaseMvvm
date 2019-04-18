package com.vti.base.functional

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.paging.PagedList

interface ModelsContainer<MODEL> {
    val modelsNavigator: LiveData<PagedList<MODEL>>
    fun getLifecycleOwner(): LifecycleOwner
}