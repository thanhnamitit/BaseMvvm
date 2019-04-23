package com.navanvine.app.screen.main

import com.navanvine.app.model.Voi
import com.vti.base.functional.ModelsProvider

class ItemVoiViewModel(val voi: Voi, val modelsProvider: ModelsProvider<Voi>) {
    fun getNum() = voi.num.toString()

    fun onClick() {
        modelsProvider.onItemClick(voi)
    }
}