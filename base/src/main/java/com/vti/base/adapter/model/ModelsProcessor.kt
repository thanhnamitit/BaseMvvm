package com.vti.base.adapter.model

import androidx.lifecycle.LifecycleOwner
import com.vti.base.adapter.AdapterCommander
import com.vti.base.adapter.viewmodel.ViewModel
import com.vti.base.extension.function.setEvent
import com.vti.base.extension.livedata.NaviLiveData
import com.vti.base.extension.livedata.event.Event
import com.vti.base.functional.Command
import com.vti.base.functional.ModelsProvider

class ModelsProcessor<MODEL>(val viewModel: ViewModel<MODEL>) : ModelsProvider<MODEL>, AdapterCommander<MODEL> {


    override fun onItemClick(model: MODEL, type: Int) {
        viewModel.onItemClick(model, type)
    }

    override val models = mutableListOf<MODEL>()

    override fun getItemCount() = models.size

    override fun getLifeCycleOwner(): LifecycleOwner {
        return viewModel.getSimpleLifecycleOwner()
    }

    override val commandNavigator = NaviLiveData<Event<Command>>()

    override fun requestLoadMore() {
        viewModel.onLoadMoreRequested()
    }

    override fun addListOfModelAtLast(models: List<MODEL>) {
        val insertedPosition = getItemCount()
        this.models.addAll(models)
        sendCommand(Command.inserted(insertedPosition, models.size))
    }

    override fun addListOfModelAtFirst(models: List<MODEL>) {
        this.models.addAll(0, models)
        sendCommand(Command.inserted(0, models.size))
    }


    override fun addModelAtFirst(model: MODEL) {
        this.models.add(0, model)
        sendCommand(Command.insertedOne(0))
    }

    override fun addModelAtLast(model: MODEL) {
        this.models.add(model)
        sendCommand(Command.insertedOne(models.size - 1))
    }

    override fun removeModel(model: MODEL) {
        val position = models.indexOf(model)
        if (position == -1) return
        removeModelAt(position)

    }

    override fun addListOfModel(position: Int, models: List<MODEL>) {
        this.models.addAll(position, models)
        sendCommand(Command.inserted(position, models.size))
    }

    override fun addModel(position: Int, model: MODEL) {
        models.add(position, model)
        sendCommand(Command.insertedOne(position))
    }

    override fun removeModelAt(position: Int) {
        models.removeAt(position)
        sendCommand(Command.removed(position))
    }

    override fun allItemsLoaded() {
        sendCommand(Command.allItemLoaded())
    }

    override fun loadMoreFailed(reason: Int) {
        sendCommand(Command.loadMoreFailed(reason))
    }

    private fun sendCommand(command: Command) {
        commandNavigator.setEvent(command)
    }

}