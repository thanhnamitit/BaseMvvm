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
import com.vti.base.message.MessageItem
import com.vti.base.message.MessageManager
import com.vti.base.provider.SimpleLifecycleOwnerProvider
import com.vti.base.util.observable.NetworkStatusObservable
import com.vti.base.view.dialog.message.DefaultMessageDialog
import com.vti.base.viewmodel.BaseViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.getViewModel
import java.util.*
import kotlin.reflect.KClass

abstract class BaseMvvmActivity<BINDING : ViewDataBinding, VM : BaseViewModel> : BaseKoinActivity(),
    SimpleLifecycleOwnerProvider {

    var activityDialog: DialogFragment? = null

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

    open fun setupObserver() {

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
        messageManager.toastMessageNavigator.observe(this, EventObserver { showToast(it.content!!) })
        messageManager.snackBarMessageNavigator.observe(this, EventObserver { showSnackBar(it.content!!) })
        messageManager.diaLogMessageNavigator.observe(this, EventObserver { showDialog(it) })
        messageManager.dismissRequestAnnouncement.observe(this, EventObserver { dismissDialog() })
    }

    private fun showToast(content: String) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
    }

    private fun showSnackBar(content: String) {
        Snackbar.make(binding.root, content, Snackbar.LENGTH_SHORT).show()
    }

    private fun showDialog(messageItem: MessageItem) {
        dismissDialog()
        activityDialog = generateDialogByMessageItem(messageItem)
        activityDialog?.dialog?.setOnCancelListener { }
        activityDialog?.show(supportFragmentManager, UUID.randomUUID().toString())
    }

    private fun dismissDialog() {
        activityDialog?.dismiss()
        activityDialog = null
    }

    open fun generateDialogByMessageItem(messageItem: MessageItem): DialogFragment {
        return DefaultMessageDialog.newInstance(messageItem)
    }

    open fun onNetworkReconnected() {}
    open fun onNetworkDisconnected() {}

    override fun getSimpleLifecycleOwner(): LifecycleOwner = viewModel

    override fun getNormalLifecycleOwner(): LifecycleOwner = this

}
