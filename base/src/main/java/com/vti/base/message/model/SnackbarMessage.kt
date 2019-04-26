package com.vti.base.message.model

import com.google.android.material.snackbar.Snackbar

class SnackbarMessage internal constructor() : Message() {
    var duration = Snackbar.LENGTH_SHORT
    var callBack: SnackbarMessage.CallBack? = null
    var actionText: String? = null

    interface CallBack {
        fun onActionClick(message: SnackbarMessage)
    }
}

