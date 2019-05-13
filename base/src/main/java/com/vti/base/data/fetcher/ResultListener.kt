package com.vti.base.data.fetcher

/**
 * Created by VTI Android Team on 3/20/2018.
 * Copyright © 2018 VTI Inc. All rights reserved.
 */
interface ResultListener<T> {

    fun onRequestStart()

    fun onRequestSuccess(response: T)

    fun onRequestError(error: Throwable)

}
