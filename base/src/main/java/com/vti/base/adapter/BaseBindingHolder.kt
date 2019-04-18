package com.vti.base.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseBindingHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    abstract fun getVariableId(): Int
    fun setVariable(viewModel: Any) {
        binding.setVariable(getVariableId(), viewModel)
    }
}
