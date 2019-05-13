package com.vti.base.message.impl

import com.vti.base.extension.function.setEvent
import com.vti.base.extension.livedata.NaviLiveData
import com.vti.base.extension.livedata.event.Event
import com.vti.base.message.MessageManager
import com.vti.base.message.model.AlertMessage
import com.vti.base.message.model.Message
import com.vti.base.message.model.SnackbarMessage
import com.vti.base.message.model.ToastMessage

class MessageManagerImpl : MessageManager {
    private val messages = mutableListOf<AlertMessage>()
    val diaLogMessageNavigator = NaviLiveData<Event<AlertMessage>>()
    val snackBarMessageNavigator = NaviLiveData<Event<SnackbarMessage>>()
    val toastMessageNavigator = NaviLiveData<Event<ToastMessage>>()

    val dismissRequestAnnouncement = NaviLiveData<Event<Boolean>>()

    private var showingMessage: AlertMessage? = null


    override fun addMessage(message: Message) {
        when (message) {
            is SnackbarMessage -> addSnackBarMessage(message)
            is ToastMessage -> addToastMessage(message)
            is AlertMessage -> addDialogMessage(message)
        }
    }


    override fun dismissCurrent() {
        dismissRequestAnnouncement.setEvent(true)
    }

    override fun dismissAll() {
        messages.clear()
        dismissRequestAnnouncement.setEvent(true)
    }

    fun addSnackBarMessage(messageItem: SnackbarMessage) {
        snackBarMessageNavigator.setEvent(messageItem)
    }

    fun addToastMessage(messageItem: ToastMessage) {
        toastMessageNavigator.setEvent(messageItem)
    }

    private fun addDialogMessage(messageItem: AlertMessage) {
        if (messageItem.canReplaceTo(showingMessage)) {
            diaLogMessageNavigator.setEvent(messageItem)
            showingMessage = messageItem
            return
        } else {
            if (!messages.contains(messageItem)) {
                messages.add(messageItem)
                messages.sort()
            }
        }
    }


}