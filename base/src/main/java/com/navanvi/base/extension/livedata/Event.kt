package com.navanvi.base.extension.livedata

class Event<T>(val value: T) {
    var hasBeenHandled = false

    val contentIfNotHandled
        get() = if (hasBeenHandled) null else value

    val peekContent
        get() = value
}