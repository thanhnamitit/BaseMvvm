package com.vti.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.vti.base.functional.ModelsContainer

abstract class BaseBindingAdapter<MODEL>(
    modelsContainer: ModelsContainer<MODEL>, itemCallBack: DiffUtil.ItemCallback<MODEL> = createDefaultComparator()
) : PagedListAdapter<MODEL, BaseBindingHolder>(itemCallBack) {
    init {
        setupToUseStableIds()
        modelsContainer.modelsNavigator.observe(modelsContainer.getLifecycleOwner(), Observer {
            submitList(it)
        })
    }

    private fun setupToUseStableIds() {
        setHasStableIds(useStableIds())
    }

    open fun useStableIds(): Boolean {
        return false
    }

    open fun includeLoadMoreItem(): Boolean {
        return false
    }

    fun isLoadMoreItemPosition(position: Int): Boolean {
        return if (!includeLoadMoreItem()) false
        else position == super.getItemCount()
    }

    fun isNormalItemPosition(position: Int): Boolean {
        if (!includeLoadMoreItem()) return true
        return !isLoadMoreItemPosition(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_LOAD_MORE -> getLoadMoreViewHolder(layoutInflater, parent)
            else -> getViewHolder(layoutInflater, parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: BaseBindingHolder, position: Int) {
        if (isNormalItemPosition(position)) holder.setVariable(getViewModelAtPosition(position))
    }

    abstract fun getViewHolder(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): BaseBindingHolder
    abstract fun getViewModelAtPosition(position: Int): Any

    open fun getLoadMoreViewHolder(inflater: LayoutInflater, parent: ViewGroup): BaseBindingHolder {
        return null!!
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (showLoadMoreItem()) 1 else 0
    }

    private fun showLoadMoreItem(): Boolean {
        return includeLoadMoreItem() && super.getItemCount() != 0
    }

    override fun getItemViewType(position: Int): Int {
        if (isLoadMoreItemPosition(position)) {
            return TYPE_LOAD_MORE
        }
        return super.getItemViewType(position)
    }

    companion object {
        fun <MODEL> createDefaultComparator() = object : DiffUtil.ItemCallback<MODEL>() {
            override fun areContentsTheSame(oldItem: MODEL, newItem: MODEL): Boolean {
                return false
            }

            override fun areItemsTheSame(oldItem: MODEL, newItem: MODEL): Boolean {
                return false
            }
        }

        private const val TYPE_LOAD_MORE = 3816626
    }

}
