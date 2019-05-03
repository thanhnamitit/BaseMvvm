package com.vti.base.view.dialog.message.choice

import com.vti.base.functional.OnItemClickListener
import com.vti.base.message.model.Item

class ItemSelectableViewModel(private val item: Item<out Any>, private val onItemClickListener: OnItemClickListener<Item<out Any>>) {
    fun getMessage() = item.message

    fun onItemClicked() {
        onItemClickListener.onItemClick(item)
    }
}