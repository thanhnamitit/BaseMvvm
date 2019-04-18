package com.navanvine.app.screen.main

import com.navanvine.app.model.Voi

class ItemVoiViewModel(val voi: Voi) {
    fun getNum() = voi.num.toString()
}