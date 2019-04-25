package com.vti.base.adapter.viewmodel

import com.vti.base.adapter.model.ModelsProcessor
import com.vti.base.provider.SimpleLifecycleOwnerProvider

fun <MODEL> ViewModel<MODEL>.addListOfModelAtLast(models: List<MODEL>) {
    modelsProcessor.addListOfModelAtLast(models)
}

fun <MODEL> ViewModel<MODEL>.addListOfModelAtFirst(models: List<MODEL>) {
    modelsProcessor.addListOfModelAtFirst(models)
}

fun <MODEL> ViewModel<MODEL>.addListOfModel(position: Int, models: List<MODEL>) {
    modelsProcessor.addListOfModel(position, models)
}

fun <MODEL> ViewModel<MODEL>.addModelAtFirst(model: MODEL) {
    modelsProcessor.addModelAtFirst(model)
}

fun <MODEL> ViewModel<MODEL>.addModelAtLast(model: MODEL) {
    modelsProcessor.addModelAtLast(model)
}

fun <MODEL> ViewModel<MODEL>.addModel(position: Int, model: MODEL) {
    modelsProcessor.addModel(position, model)
}

fun <MODEL> ViewModel<MODEL>.removeModel(model: MODEL) {
    modelsProcessor.removeModel(model)
}

fun <MODEL> ViewModel<MODEL>.removeModelAt(position: Int) {
    modelsProcessor.removeModelAt(position)
}

fun <MODEL> ViewModel<MODEL>.allItemsLoaded() {
    modelsProcessor.allItemsLoaded()
}

fun <MODEL> ViewModel<MODEL>.loadMoreFailed(reason: Int) {
    modelsProcessor.loadMoreFailed(reason)
}

fun <MODEL> ViewModel<MODEL>.indexOf(model: MODEL): Int {
    return modelsProcessor.models.indexOf(model)
}

interface ViewModel<MODEL> : SimpleLifecycleOwnerProvider {
    fun onLoadMoreRequested()
    fun onItemClick(model: MODEL, type: Int)
    val modelsProcessor: ModelsProcessor<MODEL>
}