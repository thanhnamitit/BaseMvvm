package com.vti.base.view.dialog.message

import com.vti.base.message.model.DialogMessage
import com.vti.base.viewmodel.BaseViewModel

class DefaultMessageViewModel : BaseViewModel() {
    internal lateinit var messageItem: DialogMessage

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
        messageItem.callBack?.onPositiveClick(messageItem)
    }

    fun onNegativeClick() {
        sendEvent(EventNegativeClick)
        messageItem.callBack?.onNegativeClick(messageItem)

    }
}
