package com.vti.base.view.dialog.permission

import com.vti.base.viewmodel.BaseViewModel

class RequestPermissionViewModel : BaseViewModel() {
    lateinit var permissions: Array<String>
    lateinit var message: Message
    fun messageContent() = message.content
    fun iconId() = message.iconId
    fun onRequestClicked() = sendEvent(-1)
}