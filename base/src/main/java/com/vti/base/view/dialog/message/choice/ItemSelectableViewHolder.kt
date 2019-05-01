package com.vti.base.view.dialog.message.choice

import androidx.databinding.ViewDataBinding
import com.vti.base.BR
import com.vti.base.adapter.BaseBindingHolder

class ItemSelectableViewHolder(binding: ViewDataBinding) : BaseBindingHolder(binding) {
    override fun getVariableId(): Int {
        return BR.viewModel
    }
}