package com.vti.base.message

import androidx.fragment.app.DialogFragment
import com.vti.base.message.model.AlertMessage

interface DialogFactory {
    fun generateDialog(message: AlertMessage): DialogFragment
}