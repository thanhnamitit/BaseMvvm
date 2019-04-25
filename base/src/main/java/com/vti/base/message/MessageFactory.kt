package com.vti.base.message

object MessageFactory {
    fun toast(message: String) = MessageItem.toast().apply { this.content = message }

    fun snackBar(message: String) = MessageItem.snackBar().apply { this.content = message }

    fun fullContentDialog(
        title: String, content: String, positiveContent: String, negativeContent: String, iconResId: Int
    ) = MessageItem.dialog().apply {
        this.title = title
        this.content = content
        this.positiveContent = positiveContent
        this.negativeContent = negativeContent
        this.iconResId = iconResId
    }

    fun notificationDialog(title: String, content: String, cancelContent: String, iconResId: Int) =
        MessageItem.dialog().apply {
            this.title = title
            this.content = content
            this.negativeContent = cancelContent
            this.iconResId = iconResId
        }
}