package com.navanvine.app.screen.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.navanvine.app.BR
import com.navanvine.app.databinding.ItemLoadMoreBinding
import com.navanvine.app.databinding.ItemVoiBinding
import com.navanvine.app.model.Voi
import com.vti.base.adapter.BaseBindingAdapter
import com.vti.base.adapter.BaseBindingHolder
import com.vti.base.functional.ModelsContainer

class FakeSmsAdapter(modelsContainer: ModelsContainer<Voi>) : BaseBindingAdapter<Voi>(modelsContainer) {

    override fun getViewHolder(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): BaseBindingHolder {
        return VoiHolder(ItemVoiBinding.inflate(inflater, parent, false))
    }

    override fun getViewModelAtPosition(position: Int): Any {
        val model = getItem(position)!!
        return ItemVoiViewModel(model)
    }

    override fun includeLoadMoreItem(): Boolean {
        return true
    }

    override fun getLoadMoreViewHolder(inflater: LayoutInflater, parent: ViewGroup): BaseBindingHolder {
        return VoiHolder(ItemLoadMoreBinding.inflate(inflater, parent, false))
    }
}

class VoiHolder(binding: ViewDataBinding) : BaseBindingHolder(binding) {
    override fun getVariableId(): Int {
        return BR.viewModel
    }
}

