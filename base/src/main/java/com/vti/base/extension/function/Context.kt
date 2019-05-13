package com.vti.base.extension.function

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import timber.log.Timber

fun Context.startSelfSystemSelfSettingActivity() {
    var intent = Intent()
    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    val uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    if (intent.resolveActivity(getPackageManager()) != null) {
        startActivity(intent)
    } else {
        intent = Intent(Settings.ACTION_SETTINGS)
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent)
        } else {
            Timber.e("Cannot open setting screen")
        }
    }
}