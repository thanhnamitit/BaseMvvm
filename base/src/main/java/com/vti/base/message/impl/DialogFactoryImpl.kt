package com.vti.base.message.impl

import android.Manifest
import androidx.fragment.app.DialogFragment
import com.vti.base.R
import com.vti.base.message.DialogFactory
import com.vti.base.message.SpecialType
import com.vti.base.message.model.AlertMessage
import com.vti.base.resource.functional.ResourceManager
import com.vti.base.view.dialog.message.choice.single.DefaultSingleChoiceDialog
import com.vti.base.view.dialog.message.normal.DefaultMessageDialog
import com.vti.base.view.dialog.permission.Message
import com.vti.base.view.dialog.permission.RequestPermissionDialog
import org.koin.core.KoinComponent
import org.koin.core.inject

class DialogFactoryImpl : DialogFactory, KoinComponent {
    val resourceManager: ResourceManager by inject()
    override fun generateDialog(message: AlertMessage): DialogFragment {
        return when (message.type) {
            SpecialType.Selectable -> DefaultSingleChoiceDialog.newInstance(message)
            SpecialType.LocationPermission -> RequestPermissionDialog.newInstance(Message(resourceManager.getString(R.string.app_name), R.drawable.abc_ic_star_half_black_36dp), Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
            else -> DefaultMessageDialog.newInstance(message)
        }
    }
}