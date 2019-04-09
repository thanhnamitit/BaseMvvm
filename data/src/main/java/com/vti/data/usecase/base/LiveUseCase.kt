package com.vti.data.usecase.base

import com.vti.data.fetcher.ResultListener
import com.vti.data.param.response.wrapper.Result
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
