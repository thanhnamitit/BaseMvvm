package com.vti.base.message

import java.io.Serializable


class MessageItem : Comparable<MessageItem>, Serializable {

    companion object {
        fun toast() = MessageItem(DisplayType.Toast)

        fun snackBar() = MessageItem(DisplayType.SnackBar)

        fun dialog() = MessageItem(DisplayType.Dialog)
    }

    val displayType: DisplayType
    var title: String? = null
    var content: String? = null
    var positiveContent: String? = null
    var negativeContent: String? = null
    var iconResId: Int? = null
    var priority = Priority.Low

    internal constructor(displayType: DisplayType) {
        this.displayType = displayType
    }

    override fun compareTo(other: MessageItem): Int {
        return priority.value - other.priority.value
    }

    fun canReplaceTo(messageItem: MessageItem?): Boolean {
        if (messageItem == null) return true
        return priority.value - messageItem.priority.value >= 0
    }

}