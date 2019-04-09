package com.vti.base.extension.livedata.event

import androidx.lifecycle.Observer

class EventObserver<T>(private val onEventUnhandledContent: (value: T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.contentIfNotHandled?.let {
            onEventUnhandledContent.invoke(it)
        }
    }
}