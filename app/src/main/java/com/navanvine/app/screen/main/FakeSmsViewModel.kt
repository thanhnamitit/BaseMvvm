package com.navanvine.app.screen.main

import android.os.Handler
import com.navanvine.app.model.Voi
import com.vti.base.mvvm.viewmodel.functional.ModelContainerViewModel

class FakeSmsViewModel : ModelContainerViewModel<Voi>() {
    init {
        addListOfModelAtLast(MutableList<Voi>(10, init = { Voi(it) }))
    }

    var a = 0
    override fun onLoadMoreRequested() {
        super.onLoadMoreRequested()
        Handler().postDelayed({
            a++;
            if (a < 3) {
                addListOfModelAtLast(MutableList<Voi>(10, init = { Voi(it) }))
            } else {
                loadMoreFailed(1)
            }
        }, 3000)
    }

    override fun onItemClick(model: Voi, type: Int) {
        addListOfModel(2, MutableList<Voi>(2, init = { Voi(it) }))
    }

}
