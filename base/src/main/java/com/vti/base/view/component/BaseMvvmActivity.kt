package com.vti.base.view.component

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.vti.base.extension.koin.BaseKoinActivity
import com.vti.base.extension.livedata.event.EventObserver
import com.vti.base.message.MessageManager
import com.vti.base.message.MessageManagerImpl
import com.vti.base.message.model.DialogMessage
import com.vti.base.message.model.SnackbarMessage
import com.vti.base.message.model.ToastMessage
import com.vti.base.provider.SimpleLifecycleOwnerProvider
import com.vti.base.util.observable.NetworkStatusObservable
import com.vti.base.view.dialog.message.DefaultMessageDialog
import com.vti.base.viewmodel.BaseViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.getViewModel
import java.util.*
import kotlin.reflect.KClass

abstract class BaseMvvmActivity<BINDING : ViewDataBinding, VM : BaseViewModel> : BaseKoinActivity(), SimpleLifecycleOwnerProvider {

    var dialog: DialogFragment? = null

    abstract fun getViewModelType(): KClass<VM>
    abstract fun getLayoutId(): Int
    abstract fun getLayoutVariableId(): Int

    val messageManager: MessageManager by inject()

    val binding by lazy { DataBindingUtil.setContentView(this, getLayoutId()) as BINDING }
    val viewModel: VM by lazy { getViewModel(getViewModelType()) }

    val isForeground
        get() = viewModel.isForeground


    private val networkStateObservable by lazy { NetworkStatusObservable(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(parseBundle(savedInstanceState))
        setupFirst()
        setupFeature()
        setupViewModel()
        setupViewDataBinding()
        onViewReady()
    }

    protected open fun setupFeature() {
        if (isNeedToObserveToNetworkState()) {
            observeToNetworkState()
        }
    }

    open fun parseBundle(savedInstanceState: Bundle?) = savedInstanceState
    open fun setupFirst() {}
    open fun onViewReady() {}
    open fun onReceiveEvent(event: Int) {}
    open fun setupViewModel() {
        if (viewModel.hasNotSetupYet()) {
            viewModel.normalEventNavigator.observe(this, EventObserver { onReceiveEvent(it) })
            viewModel.expressEventNavigator.observeUntilDestroyed(this, EventObserver { onReceiveEvent(it) })
        }
        lifecycle.addObserver(viewModel)
    }

    open fun setupViewDataBinding() {
        binding.setVariable(getLayoutVariableId(), viewModel)
        binding.executePendingBindings()
        binding.lifecycleOwner = this
    }

    open fun isNeedToObserveToNetworkState() = false

    fun observeToNetworkState() {
        lifecycle.addObserver(networkStateObservable)
        networkStateObservable.observe(this, Observer {
            if (it) onNetworkReconnected()
            else onNetworkDisconnected()
        })
    }

    fun setupWithMessageManager() {
        (messageManager as MessageManagerImpl).let {
            it.toastMessageNavigator.observe(this, EventObserver { showToast(it) })
            it.snackBarMessageNavigator.observe(this, EventObserver { showSnackBar(it) })
            it.diaLogMessageNavigator.observe(this, EventObserver { showDialog(it) })
            it.dismissRequestAnnouncement.observe(this, EventObserver { dismissDialog() })
        }
    }

    private fun showToast(message: ToastMessage) {
        Toast.makeText(this, message.content, message.duration).show()
    }

    private fun showSnackBar(message: SnackbarMessage) {
        val snackbar = Snackbar.make(binding.root, message.content, message.duration)
        message.actionText?.let {
            snackbar.setAction(it) {
                message.callBack?.onActionClick(message)
            }
        }
        snackbar.show()

    }

    private fun showDialog(messageItem: DialogMessage) {
        dismissDialog()
        dialog = generateDialogByMessageItem(messageItem)
        dialog?.dialog?.setOnCancelListener { }
        dialog?.show(supportFragmentManager, UUID.randomUUID().toString())
    }

    private fun dismissDialog() {
        dialog?.dismiss()
        dialog = null
    }

    open fun generateDialogByMessageItem(messageItem: DialogMessage): DialogFragment {
        return DefaultMessageDialog.newInstance(messageItem)
    }

    open fun onNetworkReconnected() {}
    open fun onNetworkDisconnected() {}

    override fun getSimpleLifecycleOwner(): LifecycleOwner = viewModel

    override fun getNormalLifecycleOwner(): LifecycleOwner = this

}
