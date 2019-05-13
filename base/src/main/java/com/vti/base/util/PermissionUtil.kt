package com.vti.base.util

import android.content.Context
import android.content.pm.PackageManager
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat

fun hasSelfPermissions(@NonNull context: Context, @NonNull permission: String): Boolean {
    return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
}

fun hasSelfPermissions(@NonNull context: Context,   permissions: Array<String>): Boolean {
    return permissions.all { hasSelfPermissions(context,it)  }
}

