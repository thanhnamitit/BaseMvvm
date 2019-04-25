package com.vti.base.view.dialog.message

import com.vti.base.message.MessageItem
import com.vti.base.viewmodel.BaseViewModel

class DefaultMessageViewModel : BaseViewModel() {
    internal lateinit var messageItem: MessageItem

    val title
        get() = messageItem.title ?: ""
    val content
        get() = messageItem.content ?: ""
    val positiveContent
        get() = messageItem.positiveContent ?: ""
    val negativeContent
        get() = messageItem.negativeContent ?: ""

    fun onPositiveClick() {
        sendEvent(EventPositiveClick)
    }

    fun onNegativeClick() {
        sendEvent(EventNegativeClick)
    }
}
