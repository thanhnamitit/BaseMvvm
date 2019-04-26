package com.navanvine.app.screen.home

import androidx.preference.Preference
import com.navanvine.app.R
import com.navanvine.app.extension.function.navigate
import com.vti.base.view.component.BaseSettingFragment

class HomeFragment : BaseSettingFragment(), Preference.OnPreferenceClickListener {

    companion object {
        const val KEY_MESSAGE_MANAGER = "key_message_manager"
    }

    override fun getXmlId(): Int {
        return R.xml.menu_main
    }

    override fun getIdsToListenOnClick(): MutableList<String>? {
        return mutableListOf(KEY_MESSAGE_MANAGER, KEY_MESSAGE_MANAGER)
    }

    override fun onPreferenceClick(preference: Preference): Boolean {
        when (preference.key) {
            KEY_MESSAGE_MANAGER -> navigate(R.id.action_homeFragment_to_messageFragment)
        }
        return true
    }

}