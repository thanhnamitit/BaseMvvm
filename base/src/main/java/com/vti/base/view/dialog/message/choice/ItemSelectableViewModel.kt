package com.vti.base.view.dialog.message.choice

import com.vti.base.functional.OnItemClickListener
import com.vti.base.message.model.Item

class ItemSelectableViewModel(private val item: Item<*>, private val onItemClickListener: OnItemClickListener<Item<*>>) {
    fun getMessage() = item.message

    fun onItemClicked() {
        onItemClickListener.onItemClick(item)
    }
}