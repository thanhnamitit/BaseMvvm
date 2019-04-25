package com.vti.base.message

import com.vti.base.extension.function.setEvent
import com.vti.base.extension.livedata.NaviLiveData
import com.vti.base.extension.livedata.event.Event

class MessageManager {
    private val messages = mutableListOf<MessageItem>()
    val diaLogMessageNavigator = NaviLiveData<Event<MessageItem>>()
    val snackBarMessageNavigator = NaviLiveData<Event<MessageItem>>()
    val toastMessageNavigator = NaviLiveData<Event<MessageItem>>()

    val dismissRequestAnnouncement = NaviLiveData<Event<Boolean>>()

    private var showingMessage: MessageItem? = null


    fun addMessage(messageItem: MessageItem) {
        when (messageItem.displayType) {
            DisplayType.Dialog -> addDialogMessage(messageItem)
            DisplayType.SnackBar -> addSnackBarMessage(messageItem)
            DisplayType.Toast -> addToastMessage(messageItem)
        }
    }

    private fun addDialogMessage(messageItem: MessageItem) {
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

    fun dismissCurrent() {
        dismissRequestAnnouncement.setEvent(true)
    }

    fun dismissAll() {
        messages.clear()
        dismissRequestAnnouncement.setEvent(true)
    }

    fun addSnackBarMessage(messageItem: MessageItem) {
        snackBarMessageNavigator.setEvent(messageItem)
    }

    fun addToastMessage(messageItem: MessageItem) {
        toastMessageNavigator.setEvent(messageItem)
    }


}