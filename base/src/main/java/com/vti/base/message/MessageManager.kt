package com.vti.base.message

import com.vti.base.message.model.Message

interface MessageManager {
    fun addMessage(message: Message)
    fun dismissCurrent()
    fun dismissAll()
}