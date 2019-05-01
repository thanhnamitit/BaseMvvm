package com.vti.base.functional

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.vti.base.extension.livedata.event.Event

interface ModelsProvider<MODEL> : OnItemClickListener<MODEL> {
    val models: List<MODEL>
    fun getItemCount(): Int
    fun getLifeCycleOwner(): LifecycleOwner
    val commandNavigator: LiveData<Event<Command>>
    fun requestLoadMore()
    fun allItemsLoaded()
    fun loadMoreFailed(reason: Int)
}