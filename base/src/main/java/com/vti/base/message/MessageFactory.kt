package com.vti.base.message

import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.vti.base.message.model.DialogMessage
import com.vti.base.message.model.SnackbarMessage
import com.vti.base.message.model.ToastMessage

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


    fun fullContentDialog(title: String, content: String, positiveContent: String, negativeContent: String, iconResId: Int, callBack: DialogMessage.CallBack? = null, id: Int = -1) = DialogMessage().apply {
        this.id = id
        this.title = title
        this.content = content
        this.positiveContent = positiveContent
        this.negativeContent = negativeContent
        this.iconResId = iconResId
        this.callBack = callBack
    }

    fun notificationDialog(title: String, content: String, cancelContent: String, iconResId: Int, callBack: DialogMessage.CallBack? = null, id: Int = -1) = DialogMessage().apply {
        this.id = id
        this.title = title
        this.content = content
        this.negativeContent = cancelContent
        this.iconResId = iconResId
        this.callBack = callBack
    }
}