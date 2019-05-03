package com.vti.base.message.model

class SelectableMessage<VALUE> : AlertMessage() {
    var doneMessage: String? = null
    lateinit var items: List<Item<VALUE>>
    var onItemSelectListener: OnItemSelectListener<VALUE>? = null
    var onItemsSelectListener: OnItemsSelectListener<VALUE>? = null
}

interface OnItemSelectListener<VALUE> {
    fun onItemSelected(item: Item<out VALUE>)
}

interface OnItemsSelectListener<VALUE> {
    fun onItemsSelected(item: Item<out VALUE>)
}
