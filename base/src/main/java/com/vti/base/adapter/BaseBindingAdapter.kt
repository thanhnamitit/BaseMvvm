package com.vti.base.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vti.base.extension.livedata.event.EventObserver
import com.vti.base.functional.Command
import com.vti.base.functional.ModelsProvider

abstract class BaseBindingAdapter<MODEL>(val modelsProvider: ModelsProvider<MODEL>) :
    RecyclerView.Adapter<BaseBindingHolder>() {

    companion object {
        const val TAG = "BaseBindingAdapter"
        const val DEFAULT_LOAD_BEFORE = 5
    }

    val models = modelsProvider.models
    var loadMoreStatus = LoadMoreStatus.IDLE
    var loadFailedReason = 0
    open val loadMoreViewHolderCreator: ((parent: ViewGroup, viewType: Int) -> BaseBindingHolder)? = null
    open val loadMoreFailedViewHolderCreator: ((parent: ViewGroup, viewType: Int, reason: Int) -> BaseBindingHolder)? =
        null

    init {
        modelsProvider.commandNavigator.observe(modelsProvider.getLifeCycleOwner(),
            EventObserver { onReceiveCommand(it) })
    }

    override fun getItemViewType(position: Int): Int {
        if (isLoadMoreItemPosition(position)) {
            return TYPE_LOAD_MORE
        }
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingHolder {
        return when (viewType) {
            TYPE_LOAD_MORE -> if (loadMoreStatus == LoadMoreStatus.LOAD_FAILED && loadMoreFailedViewHolderCreator != null) ::onCreateLoadMoreFailViewHolder else ::onCreateLoadMoreViewHolder
            else -> ::onCreateNormalViewHolder
        }.invoke(parent, viewType)

    }

    private fun isLoadMoreItemPosition(position: Int): Boolean {
        return if (!includeLoadMoreItem()) false
        else position == modelsProvider.getItemCount()
    }

    private fun isNormalItemPosition(position: Int): Boolean {
        if (!includeLoadMoreItem()) return true
        return !isLoadMoreItemPosition(position)
    }

    override fun getItemCount(): Int {
        return modelsProvider.getItemCount() + if (showLoadMoreItem()) 1 else 0
    }

    private fun showLoadMoreItem(): Boolean {
        return includeLoadMoreItem() && modelsProvider.getItemCount() != 0 && loadMoreStatus != LoadMoreStatus.OVER
    }

    private fun onReceiveCommand(command: Command) {
        when (command.type) {
            Command.ALL_DATA_CHANGED -> notifyDataSetChanged()
            Command.INSERTED_ONE -> notifyItemRangeInserted(command.position, command.count)
            Command.INSERTED_LIST -> notifyItemInserted(command.position)
            Command.REMOVED -> notifyItemRemoved(command.position)
            Command.ALL_ITEM_LOADED -> {
                loadMoreStatus = LoadMoreStatus.OVER
                notifyItemRemoved(modelsProvider.getItemCount() + 1)
                return
            }
            Command.LOAD_MORE_FAILED -> {
                loadMoreStatus = LoadMoreStatus.LOAD_FAILED
                notifyItemChanged(modelsProvider.getItemCount() + 1)
                return
            }
        }
        if (loadMoreStatus == LoadMoreStatus.WAITING) loadMoreStatus = LoadMoreStatus.IDLE
    }

    override fun onBindViewHolder(holder: BaseBindingHolder, position: Int) {
        if (includeLoadMoreItem() && isInLoadMoreRange(position)) {
            checkToRequestLoadMore()
        }
        if (isNormalItemPosition(position)) holder.setVariable(
            getViewModelAtPosition(position)
        )
    }

    fun isInLoadMoreRange(position: Int): Boolean {
        return position >= modelsProvider.getItemCount() - getLoadBefore()
    }

    private fun checkToRequestLoadMore() {
        if (loadMoreStatus == LoadMoreStatus.OVER) return
        if (loadMoreStatus != LoadMoreStatus.WAITING) {
            loadMoreStatus = LoadMoreStatus.WAITING
            modelsProvider.requestLoadMore()
        }
    }

    fun getLoadBefore(): Int {
        return DEFAULT_LOAD_BEFORE
    }

    private fun includeLoadMoreItem() = loadMoreViewHolderCreator != null
    private fun onCreateLoadMoreViewHolder(parent: ViewGroup, viewType: Int): BaseBindingHolder {
        if (loadMoreViewHolderCreator == null) throw NullPointerException("Don't know why I'm here :(. Please don't edit base code which was written by Navi, any question please contact to thanhnamitit@gmail.com")
        return loadMoreViewHolderCreator!!.invoke(parent, viewType)
    }

    private fun onCreateLoadMoreFailViewHolder(parent: ViewGroup, viewType: Int): BaseBindingHolder {
        if (loadMoreViewHolderCreator == null) throw NullPointerException("Don't know why I'm here :(. Please don't edit base code which was written by Navi, any question please contact to thanhnamitit@gmail.com")
        return loadMoreFailedViewHolderCreator!!.invoke(parent, viewType, loadFailedReason)
    }

    abstract fun onCreateNormalViewHolder(parent: ViewGroup, viewType: Int): BaseBindingHolder
    abstract fun getViewModelAtPosition(position: Int): Any
}

object LoadMoreStatus {
    const val IDLE = 0
    const val WAITING = 1
    const val OVER = 2
    const val LOAD_FAILED = 3
}