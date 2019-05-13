package com.navanvine.app.screen.message

import androidx.preference.Preference
import com.navanvine.app.R
import com.vti.base.message.MessageFactory
import com.vti.base.message.model.AlertMessage
import com.vti.base.message.model.Item
import com.vti.base.message.model.SnackbarMessage
import com.vti.base.view.component.BaseSettingFragment

class MessageFragment : BaseSettingFragment() {

    companion object {
        const val KEY_TOAST = "key_toast"
        const val KEY_SNACKBAR = "key_snackbar"
        const val KEY_SNACKBAR_WITH_ACTION = "key_snackbar_with_action"
        const val KEY_FULL_CONTENT_DIALOG = "key_full_content_dialog"
        const val KEY_CONFIRM_DIALOG = "key_confirm_dialog"
        const val KEY_SINGLE_CHOICE_DIALOG = "key_single_choice_dialog"
        const val KEY_MULTI_CHOICE_DIALOG = "key_multi_choice_dialog"
    }

    override fun getXmlId(): Int {
        return R.xml.menu_message
    }

    override fun getIdsToListenOnClick(): MutableList<String>? {
        return mutableListOf(KEY_TOAST, KEY_SNACKBAR, KEY_SNACKBAR_WITH_ACTION, KEY_FULL_CONTENT_DIALOG, KEY_CONFIRM_DIALOG, KEY_SINGLE_CHOICE_DIALOG, KEY_MULTI_CHOICE_DIALOG)
    }

    override fun onPreferenceClick(preference: Preference): Boolean {
        when (preference.key) {
            KEY_TOAST -> addToast()
            KEY_SNACKBAR -> addSnackBar()
            KEY_SNACKBAR_WITH_ACTION -> addSnackBarWithAction()
            KEY_FULL_CONTENT_DIALOG -> addFullContentDialog()
            KEY_CONFIRM_DIALOG -> addConfirmDialog()
            KEY_SINGLE_CHOICE_DIALOG -> addSingleChoiceDialog()
            KEY_MULTI_CHOICE_DIALOG -> addRequestPermissionDialog()
        }
        return super.onPreferenceClick(preference)
    }

    private fun addConfirmDialog() {

    }

    private fun addFullContentDialog() {
        messageManager.addMessage(MessageFactory.fullContent("Title", "Message", "Positive", " Negative", R.drawable.navigation_empty_icon))
    }

    private fun addSnackBarWithAction() {
        messageManager.addMessage(MessageFactory.snackbar("Snackbar content with action", actionText = "Action", callBack = object : SnackbarMessage.CallBack {
            override fun onActionClick(message: SnackbarMessage) {
                messageManager.addMessage(MessageFactory.toast("clicked action button ^^"))
            }
        }))
    }

    private fun addSnackBar() {
        messageManager.addMessage(MessageFactory.snackbar("Snackbar content"))
    }

    private fun addToast() {
        messageManager.addMessage(MessageFactory.toast("Toast content"))
    }

    private fun addSingleChoiceDialog() {
        messageManager.addMessage(MessageFactory.singleChoice("Single choice title", "Cancel", MutableList(8) { Item(it.toString(), it.toString()) }, object : AlertMessage.OnItemSelectListener {
            override fun onItemSelected(item: Item<out Any>) {
                messageManager.addMessage(MessageFactory.toast("clicked ${item.message} ^^"))
            }

        }))
    }

    private fun addRequestPermissionDialog() {
        messageManager.addMessage(MessageFactory.locationPermissionRequest())
    }


}