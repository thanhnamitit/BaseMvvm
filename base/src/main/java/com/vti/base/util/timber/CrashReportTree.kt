package com.vti.base.util.timber

import android.util.Log
import timber.log.Timber

/**
 * Created by VTI Android Team on 3/21/2018.
 * Copyright © 2018 VTI Inc. All rights reserved.
 */
class CrashReportTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
            return
        }

        //TODO: Do some works here: fabric logging or something
    }

}
