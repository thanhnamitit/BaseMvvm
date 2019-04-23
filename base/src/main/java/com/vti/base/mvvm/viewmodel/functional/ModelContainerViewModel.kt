package com.vti.base.mvvm.viewmodel.functional

import com.vti.base.adapter.AdapterCommander
import com.vti.base.adapter.model.ModelsProcessor
import com.vti.base.adapter.viewmodel.ViewModel
import com.vti.base.mvvm.viewmodel.BaseViewModel

open class ModelContainerViewModel<MODEL>() : BaseViewModel(), ViewModel<MODEL>, AdapterCommander<MODEL> {
    val modelsProcessor = ModelsProcessor(this)

    override fun addListOfModelAtLast(models: List<MODEL>) {
        modelsProcessor.addListOfModelAtLast(models)
    }

    override fun addListOfModelAtFirst(models: List<MODEL>) {
        modelsProcessor.addListOfModelAtFirst(models)
    }

    override fun addListOfModel(position: Int, models: List<MODEL>) {
        modelsProcessor.addListOfModel(position, models)
    }

    override fun addModelAtFirst(model: MODEL) {
        modelsProcessor.addModelAtFirst(model)
    }

    override fun addModelAtLast(model: MODEL) {
        modelsProcessor.addModelAtLast(model)
    }

    override fun addModel(position: Int, model: MODEL) {
        modelsProcessor.addModel(position, model)
    }

    override fun removeModel(model: MODEL) {
        modelsProcessor.removeModel(model)
    }

    override fun removeModelAt(position: Int) {
        modelsProcessor.removeModelAt(position)
    }

    fun allItemsLoaded() {
        modelsProcessor.allItemsLoaded()
    }

    fun loadMoreFailed(reason: Int) {
        modelsProcessor.loadMoreFailed(reason)
    }

    override fun onLoadMoreRequested() {
    }

    override fun onItemClick(model: MODEL, type: Int) {
    }


}