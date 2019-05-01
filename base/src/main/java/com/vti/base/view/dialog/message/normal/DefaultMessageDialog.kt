package com.vti.base.view.dialog.message.normal

import android.os.Bundle
import com.vti.base.BR
import com.vti.base.R
import com.vti.base.databinding.DefaultDialogMessageBinding
import com.vti.base.message.model.AlertMessage
import com.vti.base.view.component.BaseMvvmDialogFragment
import com.vti.base.view.dialog.message.EventNegativeClick
import com.vti.base.view.dialog.message.EventPositiveClick
import kotlin.reflect.KClass

class DefaultMessageDialog : BaseMvvmDialogFragment<DefaultDialogMessageBinding, DefaultMessageViewModel>() {

    companion object {
        const val KEY_MESSAGE_ITEM = "AlertMessage"
        fun newInstance(message: AlertMessage) = DefaultMessageDialog().apply {
            arguments = Bundle().apply {
                putSerializable(KEY_MESSAGE_ITEM, message)
            }
        }
    }

    override fun getViewModelType(): KClass<DefaultMessageViewModel> {
        return DefaultMessageViewModel::class
    }

    override fun getLayoutId(): Int {
        return R.layout.default_dialog_message
    }

    override fun getLayoutVariableId(): Int {
        return BR.viewModel
    }

    override fun handleArguments(arguments: Bundle) {
        super.handleArguments(arguments)
        viewModel.messageItem = arguments.getSerializable(KEY_MESSAGE_ITEM) as AlertMessage
    }

    override fun onReceiveEvent(event: Int) {
        super.onReceiveEvent(event)
        val callBack = getActivity() as? AlertMessage.CallBack
        when (event) {
            EventPositiveClick -> {
                callBack?.onPositiveClick(viewModel.messageItem)
            }
            EventNegativeClick -> {
                callBack?.onNegativeClick(viewModel.messageItem)
            }
        }
        dismiss()
    }
}

