package com.vti.base.resource.executor

import android.content.Context
import androidx.core.content.ContextCompat
import com.vti.base.resource.functional.ResourceManager
import org.koin.core.KoinComponent
import org.koin.core.inject

class ResourceManagerImpl() : ResourceManager, KoinComponent {


    val context: Context by inject()
    override fun getString(id: Int): String {
        return context.getString(id)
    }

    override fun getColor(id: Int): Int {
        return ContextCompat.getColor(context, id)
    }
}