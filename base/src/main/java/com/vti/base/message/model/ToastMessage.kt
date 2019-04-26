package com.vti.base.message.model

import android.widget.Toast

class ToastMessage internal constructor() : Message() {
    var duration = Toast.LENGTH_SHORT
}