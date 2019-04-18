package com.navanvine.app.screen.main

import android.os.Handler
import com.navanvine.app.model.Voi
import com.vti.base.mvvm.viewmodel.functional.ModelsContainerViewModel

class FakeSmsViewModel : ModelsContainerViewModel<Voi>() {
    val handler = Handler()
    override fun loadMore(size: Int, callback: (datas: List<Voi>) -> Unit) {
        super.loadMore(size, callback)
        var num = 0
        modelsNavigator.value?.let {
            num = it.last()!!.num
        }
        handler.postDelayed({
            callback.invoke(mutableListOf<Voi>().apply {
                (1..10).map {
                    add(Voi(it + num))
                }
            })
        }, 5000)
    }
}
