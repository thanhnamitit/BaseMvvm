package com.vti.base.view.component

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.vti.base.message.MessageManager
import org.koin.android.ext.android.inject


abstract class BaseSettingFragment : PreferenceFragmentCompat(), Preference.OnPreferenceClickListener {


    val messageManager: MessageManager by inject()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(getXmlId(), rootKey)
        getIdsToListenOnClick()?.let {
            listenClickFor(it)
        }
    }

    private fun listenClickFor(strings: MutableList<String>) {
        strings.map {
            findPreference(it).onPreferenceClickListener = this
        }
    }

    open fun getIdsToListenOnClick(): MutableList<String>? {
        return null
    }

    override fun onPreferenceClick(preference: Preference): Boolean {
        return true
    }

    abstract fun getXmlId(): Int

}