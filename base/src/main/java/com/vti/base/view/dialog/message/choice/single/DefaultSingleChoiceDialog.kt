package com.vti.base.view.dialog.message.choice.single

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vti.base.BR
import com.vti.base.R
import com.vti.base.databinding.DefaultDialogSingleChoiceBinding
import com.vti.base.message.model.SelectableMessage
import com.vti.base.view.component.BaseMvvmDialogFragment
import com.vti.base.view.dialog.message.normal.DefaultMessageDialog
import kotlin.reflect.KClass

class DefaultSingleChoiceDialog : BaseMvvmDialogFragment<DefaultDialogSingleChoiceBinding, DefaultSingleChoiceViewModel>() {
    companion object {
        const val KEY_MESSAGE_ITEM = "AlertMessage"
        fun newInstance(message: SelectableMessage<*>) = DefaultSingleChoiceDialog().apply {
            arguments = Bundle().apply {
                putSerializable(KEY_MESSAGE_ITEM, message)
            }
        }
    }

    override fun getViewModelType(): KClass<DefaultSingleChoiceViewModel> {
        return DefaultSingleChoiceViewModel::class
    }

    override fun getLayoutId(): Int {
        return R.layout.default_dialog_single_choice
    }

    override fun getLayoutVariableId(): Int {
        return BR.viewModel
    }

    override fun handleArguments(arguments: Bundle) {
        super.handleArguments(arguments)
        viewModel.messageItem = arguments.getSerializable(DefaultMessageDialog.KEY_MESSAGE_ITEM) as SelectableMessage<*>?
    }

    override fun onViewReady() {
        super.onViewReady()
        setupRecyclerView(binding.recyclerView)
    }

    fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = DefaultSingleChoiceDialogAdapter(viewModel.modelsProcessor)
    }
}