package com.vti.base.view.dialog.message.normal

import com.vti.base.message.model.AlertMessage
import com.vti.base.view.dialog.message.EventNegativeClick
import com.vti.base.view.dialog.message.EventPositiveClick
import com.vti.base.viewmodel.BaseViewModel

class DefaultMessageViewModel : BaseViewModel() {
    internal lateinit var messageItem: AlertMessage

    val title
        get() = messageItem.title ?: ""
    val content
        get() = messageItem.content
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
