package com.vti.base.viewmodel.functional

import com.vti.base.adapter.model.ModelsProcessor
import com.vti.base.adapter.viewmodel.ViewModel
import com.vti.base.viewmodel.BaseViewModel

open class ModelContainerViewModel<MODEL>() : BaseViewModel(), ViewModel<MODEL> {

    override val modelsProcessor = ModelsProcessor(this)


    override fun onLoadMoreRequested() {
    }

    override fun onItemClick(model: MODEL, type: Int) {
    }


}