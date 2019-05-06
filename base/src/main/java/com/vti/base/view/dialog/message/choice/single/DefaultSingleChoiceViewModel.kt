package com.vti.base.view.dialog.message.choice.single

import com.vti.base.adapter.viewmodel.addListOfModelAtFirst
import com.vti.base.message.model.Item
import com.vti.base.message.model.SelectableMessage
import com.vti.base.view.dialog.message.EventNegativeClick
import com.vti.base.view.dialog.message.EventPositiveClick
import com.vti.base.viewmodel.functional.ModelsContainerViewModel

class DefaultSingleChoiceViewModel : ModelsContainerViewModel<Item<out Any>>() {
    var messageItem: SelectableMessage<Any>? = null
        set(value) {
            value?.let {
                addListOfModelAtFirst(it.items)
            }
            field = value
        }

    val title
        get() = messageItem?.title ?: ""
    val doneContent
        get() = messageItem?.doneMessage ?: ""


    override fun onItemClick(model: Item<out Any>, type: Int) {
        super.onItemClick(model, type)
        messageItem?.onItemSelectListener?.let {
            it.onItemSelected(model)
            sendEvent(EventPositiveClick)
            return
        }
    }

    fun onDoneClick() {
        sendEvent(EventNegativeClick)
    }
}