package com.vti.base.view.dialog.message.choice.single

import android.view.LayoutInflater
import android.view.ViewGroup
import com.vti.base.adapter.BaseBindingAdapter
import com.vti.base.adapter.BaseBindingHolder
import com.vti.base.databinding.ItemChoosableBinding
import com.vti.base.functional.ModelsProvider
import com.vti.base.message.model.Item
import com.vti.base.view.dialog.message.choice.ItemSelectableViewHolder
import com.vti.base.view.dialog.message.choice.ItemSelectableViewModel

class DefaultSingleChoiceDialogAdapter(modelsProvider: ModelsProvider<Item<*>>) : BaseBindingAdapter<Item<*>>(modelsProvider) {
    override fun onCreateNormalViewHolder(parent: ViewGroup, viewType: Int): BaseBindingHolder {
        return ItemSelectableViewHolder(ItemChoosableBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getViewModelAtPosition(position: Int): Any {
        return ItemSelectableViewModel(models.get(position), modelsProvider)
    }
}