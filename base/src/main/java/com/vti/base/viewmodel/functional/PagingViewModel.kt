package com.vti.base.viewmodel.functional

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.vti.base.functional.ModelsContainer
import com.vti.base.viewmodel.BaseViewModel

open class PagingViewModel<MODEL> : BaseViewModel(), ModelsContainer<MODEL> {
    final override val modelsNavigator: LiveData<PagedList<MODEL>>

    init {
        modelsNavigator = getLivePagedListBuilder().build()
    }

    override fun getLifecycleOwner(): LifecycleOwner {
        return this
    }


    fun getLivePagedListBuilder(): LivePagedListBuilder<Int, MODEL> {
        return LivePagedListBuilder<Int, MODEL>(getDataSourceFactory(), getPagedListConfig())
    }

    fun getPagedListConfig(): PagedList.Config {
        return PagedList.Config.Builder().setPageSize(20).setInitialLoadSizeHint(20).setEnablePlaceholders(false)
            .build()
    }

    fun getDataSourceFactory(): DataSource.Factory<Int, MODEL> {
        return DefaultFactory()
    }

    open fun loadMore(size: Int, callback: (datas: List<MODEL>) -> Unit) {
    }

    inner class DefaultDataSource : PageKeyedDataSource<Int, MODEL>() {
        override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, MODEL>) {
            loadMore(params.requestedLoadSize) {
                callback.onResult(it, null, 2)
            }
        }

        override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MODEL>) {
            loadMore(params.requestedLoadSize) {
                callback.onResult(it, params.key + 1)
            }
        }

        override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MODEL>) {
        }
    }

    inner class DefaultFactory : DataSource.Factory<Int, MODEL>() {
        override fun create(): DataSource<Int, MODEL> {
            return DefaultDataSource()
        }

    }

    companion object {

    }

}

