package com.vti.base.adapter.viewmodel

import androidx.lifecycle.LifecycleOwner
import com.vti.base.adapter.AdapterCommander
import com.vti.base.mvvm.SimpleLifecycleOwnerProvider

interface ViewModel<MODEL> : SimpleLifecycleOwnerProvider {
    fun onLoadMoreRequested()
    fun onItemClick(model: MODEL, type: Int)
}