package com.vti.base.extension.function

import androidx.fragment.app.Fragment

fun Fragment.hasSelfPermissions(permissions: Array<String>): Boolean {
    return com.vti.base.util.hasSelfPermissions(requireContext(), permissions)
}

fun Fragment.hasSelfPermission(permission: String): Boolean {
    return com.vti.base.util.hasSelfPermissions(requireContext(), permission)
}

fun Fragment.shouldShowRequestPermissionsRationale(permissions: Array<String>): Boolean {
    return permissions.any { shouldShowRequestPermissionRationale(it) }
}

fun Fragment.startSelfSystemSelfSettingActivity() {
    return requireContext().startSelfSystemSelfSettingActivity()
}