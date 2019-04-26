package com.vti.base.message

import com.vti.base.extension.function.setEvent
import com.vti.base.extension.livedata.NaviLiveData
import com.vti.base.extension.livedata.event.Event
import com.vti.base.message.model.DialogMessage
import com.vti.base.message.model.Message
import com.vti.base.message.model.SnackbarMessage
import com.vti.base.message.model.ToastMessage

class MessageManagerImpl : MessageManager {
    private val messages = mutableListOf<DialogMessage>()
    val diaLogMessageNavigator = NaviLiveData<Event<DialogMessage>>()
    val snackBarMessageNavigator = NaviLiveData<Event<SnackbarMessage>>()
    val toastMessageNavigator = NaviLiveData<Event<ToastMessage>>()

    val dismissRequestAnnouncement = NaviLiveData<Event<Boolean>>()

    private var showingMessage: DialogMessage? = null


    override fun addMessage(message: Message) {
        when (message) {
            is SnackbarMessage -> addSnackBarMessage(message)
            is ToastMessage -> addToastMessage(message)
            is DialogMessage -> addDialogMessage(message)
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

    private fun addDialogMessage(messageItem: DialogMessage) {
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