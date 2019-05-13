package com.vti.base.view.dialog.permission

import android.content.pm.PackageManager
import android.os.Bundle
import com.vti.base.BR
import com.vti.base.R
import com.vti.base.databinding.DialogRequestPermissionBinding
import com.vti.base.extension.function.hasSelfPermissions
import com.vti.base.extension.function.shouldShowRequestPermissionsRationale
import com.vti.base.extension.function.startSelfSystemSelfSettingActivity
import com.vti.base.view.component.BaseMvvmDialogFragment
import com.vti.base.view.component.DialogType

class RequestPermissionDialog : BaseMvvmDialogFragment<DialogRequestPermissionBinding, RequestPermissionViewModel>() {
    override fun getViewModelType() = RequestPermissionViewModel::class
    override fun getLayoutId() = R.layout.dialog_request_permission
    override fun getLayoutVariableId() = BR.viewModel

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun handleArguments(arguments: Bundle) {
        super.handleArguments(arguments)
        viewModel.message = arguments.getSerializable(ARG_MESSAGE) as Message
        viewModel.permissions = arguments.getStringArray(ARG_PERMISSIONS)
    }

    override fun onViewReady() {
        super.onViewReady()
        isCancelable = false
    }

    override fun onReceiveEvent(event: Int) {
        super.onReceiveEvent(event)
        startRequest()
    }

    fun startRequest() {
        if (shouldShowRequestPermissionsRationale(viewModel.permissions)) {
            startSelfSystemSelfSettingActivity()
        } else {
            requestPermissions(viewModel.permissions, REQUESR_CODE)
        }
    }

    override fun getDialogType(): DialogType {
        return DialogType.FullScreen
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            notifyRequestSuccess()
            messageManager.dismissCurrent()
        }
    }

    fun notifyRequestSuccess() {
        (targetFragment as? OnPermissionRequestListener)?.onRequestSuccess(viewModel.permissions)
        (activity as? OnPermissionRequestListener)?.onRequestSuccess(viewModel.permissions)
    }

    override fun onResume() {
        super.onResume()
        if (hasSelfPermissions(viewModel.permissions)) {
            notifyRequestSuccess()
            messageManager.dismissCurrent()
        }
    }


    companion object {
        const val REQUESR_CODE = 6626
        const val ARG_MESSAGE = "message"
        const val ARG_PERMISSIONS = "permisions"
        fun newInstance(message: Message, vararg permissions: String): RequestPermissionDialog {
            val result = RequestPermissionDialog()
            val args = Bundle()
            args.putSerializable(ARG_MESSAGE, message)
            args.putStringArray(ARG_PERMISSIONS, permissions)
            result.setArguments(args)
            return result
        }
    }
}