package com.vti.base.data.fetcher


import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class Fetcher constructor() {
    private val disposable: CompositeDisposable

    init {
        disposable = CompositeDisposable()
    }

    fun <T> fetch(flowable: Flowable<out T>, resultListener: ResultListener<T>): Disposable {
        val target = flowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _ -> resultListener.onRequestStart() }
            .subscribe({ resultListener.onRequestSuccess(it) }, { resultListener.onRequestError(it) })
        disposable.add(target)
        return target
    }

    fun <T> fetch(observable: Observable<out T>, resultListener: ResultListener<T>): Disposable {
        val target = observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .doOnError { throwable -> throwable.printStackTrace() }
            .doOnSubscribe { _ -> resultListener.onRequestStart() }
            .subscribe({ resultListener.onRequestSuccess(it) }, { resultListener.onRequestError(it) })
        disposable.add(target)
        return target
    }

    fun <T> fetch(completable: Completable, resultListener: ResultListener<T>): Disposable {
        val target = completable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _ -> resultListener.onRequestStart() }.subscribe(object : Action {
                override fun run() {
                    //   resultListener.onRequestSuccess(null)
                }
            }, Consumer { resultListener.onRequestError(it) })
        disposable.add(target)
        return target
    }

    fun <T> fetch(single: Single<out T>, resultListener: ResultListener<T>): Disposable {
        val target = single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _ -> resultListener.onRequestStart() }
            .subscribe({ resultListener.onRequestSuccess(it) }, { resultListener.onRequestError(it) })
        disposable.add(target)
        return target
    }

    fun countdown(observable: Observable<Long>, countdownListener: CountdownListener, period: Long): Disposable {
        val target =
            observable.observeOn(AndroidSchedulers.mainThread()).doOnSubscribe { _ -> countdownListener.onStart() }
                .doOnNext { calculatedTime -> countdownListener.onTick(period - calculatedTime) }
                .takeUntil { calculatedTime -> calculatedTime == period }.subscribe()
        disposable.add(target)
        return target
    }

    fun cancelAllRequest() {
        disposable.clear();
    }

    fun cancelRequest(request: Disposable) {
        disposable.remove(request)
    }
}
