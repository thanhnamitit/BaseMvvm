package com.vti.base.view.dialog.message.choice.single

import com.vti.base.adapter.viewmodel.addListOfModelAtFirst
import com.vti.base.message.model.Item
import com.vti.base.message.model.SelectableMessage
import com.vti.base.view.dialog.message.EventNegativeClick
import com.vti.base.view.dialog.message.EventPositiveClick
import com.vti.base.viewmodel.functional.ModelsContainerViewModel

class DefaultSingleChoiceViewModel : ModelsContainerViewModel<Item<*>>() {
    var messageItem: SelectableMessage<*>? = null
        set(value) {
            value?.let {
                addListOfModelAtFirst(it.items)
            }
        }



    override fun onItemClick(model: Item<*>, type: Int) {
        super.onItemClick(model, type)
        messageItem?.onItemSelectListener?.let {
            it.onItemSelected(model)
            sendEvent(EventPositiveClick)
            return
        }    }

    fun onDoneClick() {
        sendEvent(EventNegativeClick)
    }
}