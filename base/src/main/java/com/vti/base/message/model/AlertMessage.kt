package com.vti.base.message.model

import com.vti.base.message.Priority
import java.io.Serializable


open class AlertMessage internal constructor() : Message(), Comparable<AlertMessage>, Serializable {

    var title: String? = null
    var positiveContent: String? = null
    var negativeContent: String? = null
    var iconResId: Int? = null
    var priority = Priority.Low
    var callBack: CallBack? = null


    override fun compareTo(other: AlertMessage): Int {
        return priority.value - other.priority.value
    }

    fun canReplaceTo(messageItem: AlertMessage?): Boolean {
        if (messageItem == null) return true
        return priority.value - messageItem.priority.value >= 0
    }

    interface CallBack {
        fun onPositiveClick(messageItem: AlertMessage)
        fun onNegativeClick(messageItem: AlertMessage)
    }

}