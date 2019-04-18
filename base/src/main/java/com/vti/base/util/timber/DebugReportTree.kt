package com.vti.base.util.timber

import timber.log.Timber

/**
 * Created by VTI Android Team on 3/21/2018.
 * Copyright © 2018 VTI Inc. All rights reserved.
 */
class DebugReportTree : Timber.DebugTree() {

    override fun createStackElementTag(element: StackTraceElement): String? {
        return String.format(
            "[C:%s] [M:%s] [L:%s]", super.createStackElementTag(element), element.methodName, element.lineNumber
        )

    }

}
