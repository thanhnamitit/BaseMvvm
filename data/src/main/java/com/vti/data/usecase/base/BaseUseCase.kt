package com.vti.data.usecase.base

import android.util.Pair
import androidx.lifecycle.MutableLiveData
import com.vti.data.fetcher.Fetcher
import com.vti.data.param.response.wrapper.Result
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference
import javax.inject.Inject

abstract class BaseUseCase<R, P> : UseCase {

    @Inject
    lateinit var fetcher: Fetcher

    private var disposableWeakReference = WeakReference<Disposable>(null)

    protected abstract operator fun invoke(params: P): Disposable

    //TODO: Need remove
    private val dataSource = MutableLiveData<Pair<P, Result<R>>>()

    private val response = MutableLiveData<Result<R>>()

    fun getDataSource(): MutableLiveData<Pair<P, Result<R>>> {
        return dataSource
    }

    fun getResult(): MutableLiveData<Result<R>> {
        return response
    }

    protected fun sendDataToObserver(params: P, result: Result<R>) {
        dataSource.setValue(Pair(params, result))
        response.setValue(result)
    }

    fun run(params: P) {
        val target = invoke(params)
        disposableWeakReference = WeakReference(target)
    }


    override val isRunning: Boolean
        get() {
            val target = disposableWeakReference.get()
            return target != null && !target.isDisposed
        }


    override fun cancel() {
        val target = disposableWeakReference.get()
        if (target != null && !target.isDisposed) {
            target.dispose()
            disposableWeakReference = WeakReference<Disposable>(null)
        }
    }


    override fun cancelIfRunning() {
        if (isRunning) cancel()
    }
}
