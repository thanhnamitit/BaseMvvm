package com.vti.base.message

enum class DisplayType(val type: Int) {
    Dialog(1), Toast(2), SnackBar(3)
}

enum class Priority(val value: Int) {
    Low(1), Medium(2), High(3)
}

enum class SpecialType {
    LocationPermission, NetworkError, Loading, Default, Selectable
}