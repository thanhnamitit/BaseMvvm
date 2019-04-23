package com.vti.base.extension.livedata.event

class Event<T>(val value: T?) {
    var hasBeenHandled = false

    val contentIfNotHandled
        get() = if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            value
        }
    val peekContent
        get() = value
}