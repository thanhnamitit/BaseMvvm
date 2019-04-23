package com.navanvine.app.screen.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.navanvine.app.BR
import com.navanvine.app.databinding.ItemLoadMoreBinding
import com.navanvine.app.databinding.ItemLoadMoreFailedBinding
import com.navanvine.app.databinding.ItemVoiBinding
import com.navanvine.app.model.Voi
import com.vti.base.adapter.BaseBindingAdapter
import com.vti.base.adapter.BaseBindingHolder
import com.vti.base.functional.ModelsProvider

class FakeSmsAdapter(modelsContainer: ModelsProvider<Voi>) : BaseBindingAdapter<Voi>(modelsContainer) {
    override fun onCreateNormalViewHolder(parent: ViewGroup, viewType: Int): BaseBindingHolder {
        return VoiHolder(ItemVoiBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getViewModelAtPosition(position: Int): Any {
        return ItemVoiViewModel(models[position], modelsProvider)
    }

    override val loadMoreViewHolderCreator: ((parent: ViewGroup, viewType: Int) -> BaseBindingHolder)? = { parent, _ ->
        VoiHolder(ItemLoadMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override val loadMoreFailedViewHolderCreator: ((parent: ViewGroup, viewType: Int, reason: Int) -> BaseBindingHolder)? = { parent, _, _ ->
        VoiHolder(ItemLoadMoreFailedBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

}

class VoiHolder(binding: ViewDataBinding) : BaseBindingHolder(binding) {
    override fun getVariableId(): Int {
        return BR.viewModel
    }
}

