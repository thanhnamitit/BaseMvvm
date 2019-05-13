package com.vti.base.data.usecase.base

import com.vti.base.data.fetcher.ResultListener
import com.vti.base.data.param.response.wrapper.Result
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

abstract class LiveUseCase<R, P> : BaseUseCase<R, P>() {

    protected abstract fun create(params: P): Observable<Result<R>>

    override fun invoke(params: P): Disposable {
        val observable = create(params)

        return fetcher.fetch(observable, object : ResultListener<Result<R>> {
            override fun onRequestStart() {
                sendDataToObserver(params, Result.loading<R>())
            }

            override fun onRequestSuccess(response: Result<R>) {
                sendDataToObserver(params, response)
            }

            override fun onRequestError(error: Throwable) {
                error.printStackTrace()
                sendDataToObserver(params, Result.fromError<R>(error))
            }
        })
    }

}
