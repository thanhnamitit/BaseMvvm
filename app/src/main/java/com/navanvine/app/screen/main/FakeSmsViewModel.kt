package com.navanvine.app.screen.main

import android.os.Handler
import com.navanvine.app.model.Voi
import com.vti.base.adapter.viewmodel.addListOfModel
import com.vti.base.adapter.viewmodel.addListOfModelAtLast
import com.vti.base.adapter.viewmodel.allItemsLoaded
import com.vti.base.adapter.viewmodel.indexOf
import com.vti.base.message.MessageFactory
import com.vti.base.message.model.AlertMessage
import com.vti.base.viewmodel.functional.ModelsContainerViewModel

class FakeSmsViewModel : ModelsContainerViewModel<Voi>(), AlertMessage.CallBack {

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
                allItemsLoaded()
            }
        }, 3000)
    }

    override fun onItemClick(model: Voi, type: Int) {
        addListOfModel(indexOf(model) + 1, MutableList<Voi>(2, init = { Voi(it) }))
    }

    fun showToast() {
        addMessage(MessageFactory.toast("toast content"))
    }

    fun showDialog() {
        addMessage(MessageFactory.fullContent("he", "chiu", "pom", "piu", 1, this))

    }

    fun showSnackar() {
        addMessage(MessageFactory.snackbar("Snackbar content"))
    }

    override fun onPositiveClick(messageItem: AlertMessage) {
        addMessage(MessageFactory.toast("Positive click"))
    }

    override fun onNegativeClick(messageItem: AlertMessage) {
        addMessage(MessageFactory.toast("Negative click"))

    }


}
