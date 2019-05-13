package com.vti.base.data.usecase.base

import androidx.lifecycle.MutableLiveData
import com.vti.base.data.fetcher.CountdownListener
import com.vti.base.data.fetcher.Fetcher
import com.vti.base.data.param.response.wrapper.Result
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import org.koin.core.inject
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

abstract class CountdownUseCase : UseCase {

    val fetcher: Fetcher by inject()

    val timeTick = MutableLiveData<Result<Long>>()
    private var disposableWeakReference = WeakReference<Disposable>(null)

    protected val initialDelay: Long
        get() = 0

    abstract val period: Long

    protected abstract val timeUnit: TimeUnit

    override val isRunning: Boolean
        get() {
            val target = disposableWeakReference.get()
            return target != null && !target.isDisposed
        }

    fun run() {
        val period = period
        var delay = initialDelay
        val timeUnit = timeUnit
        if (period <= 0) {
            throw IllegalArgumentException("Period cannot less than zero")
        }
        if (delay < 0) {
            delay = 0
        }
        val schedule = Observable.interval(delay, 1, timeUnit).observeOn(AndroidSchedulers.mainThread())
        val target = fetcher.countdown(schedule, ScheduleCallback(), period)
        disposableWeakReference = WeakReference(target)
    }

    override fun cancel() {
        val target = disposableWeakReference.get()
        if (target != null && !target.isDisposed) {
            fetcher.cancelRequest(target)
            target.dispose()
            disposableWeakReference = WeakReference<Disposable>(null)
        }
    }

    override fun cancelIfRunning() {
        if (isRunning) cancel()
    }

    private inner class ScheduleCallback : CountdownListener {

        override fun onStart() {
            timeTick.value = Result.fromData(period)
        }

        override fun onTick(tick: Long) {
            timeTick.value = Result.fromData(tick)
        }

    }
}
