package com.vti.base.extension.function

import com.vti.base.extension.livedata.NaviLiveData
import com.vti.base.extension.livedata.event.Event

fun <T> NaviLiveData<Event<T>>.setEvent(t: T) {
    setValue(Event(t))
}