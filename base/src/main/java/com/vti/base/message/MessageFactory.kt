package com.vti.base.message

import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.vti.base.message.model.*

object MessageFactory {
    fun toast(message: String, duration: Int = Toast.LENGTH_SHORT, id: Int = -1) = ToastMessage().apply {
        this.id = id
        this.content = message
        this.duration = duration
    }

    fun snackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT, actionText: String? = null, callBack: SnackbarMessage.CallBack? = null, id: Int = -1) = SnackbarMessage().apply {
        this.id = id
        this.content = message
        this.duration = duration
        this.actionText = actionText
        this.callBack = callBack
    }


    fun fullContent(title: String, content: String, positiveContent: String, negativeContent: String, iconResId: Int, callBack: AlertMessage.CallBack? = null, id: Int = -1) = AlertMessage().apply {
        this.id = id
        this.title = title
        this.content = content
        this.positiveContent = positiveContent
        this.negativeContent = negativeContent
        this.iconResId = iconResId
        this.callBack = callBack
    }

    fun alert(title: String, content: String, cancelContent: String, iconResId: Int, callBack: AlertMessage.CallBack? = null, id: Int = -1) = AlertMessage().apply {
        this.id = id
        this.title = title
        this.content = content
        this.negativeContent = cancelContent
        this.iconResId = iconResId
        this.callBack = callBack
    }

    fun <VALUE> singleChoice(title: String, doneMessage: String, items: List<Item<VALUE>>, onItemSelectListener: OnItemSelectListener<VALUE>) = SelectableMessage<VALUE>().apply {
        this.title = title
        this.doneMessage = doneMessage
        this.items = items
        this.onItemSelectListener = onItemSelectListener
    }
}