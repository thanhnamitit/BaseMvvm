package com.vti.base.navigation.excutor

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import com.vti.base.databinding.ToolbarBinding
import com.vti.base.extension.livedata.NaviLiveData
import com.vti.base.navigation.Navigation
import com.vti.base.navigation.NavigationInformation
import com.vti.base.resource.functional.ResourceManager
import org.koin.core.KoinComponent
import org.koin.core.inject

class NavigationImpl(navigation: NavigationInformation, val toolbarContainer: ToolbarBinding) : Navigation, KoinComponent {
    override val navigationBackgroundColor = NaviLiveData<Int>()

    val context: Context
    val toolbar: Toolbar = toolbarContainer.toolbar
    val resourceManager: ResourceManager by inject()

    init {
        this.context = toolbar.context
        setupToolbar(navigation)
    }

    fun setupToolbar(navigation: NavigationInformation) {
        if (navigation.titleId != NavigationInformation.NOT_HANDLE_RESOURCE_ID_VALUE) {
            updateTitle(navigation.titleId)
        } else {
            updateTitle(navigation.title)
        }
        navigation.titleTextColor.takeIf { it != NavigationInformation.NOT_HANDLE_RESOURCE_ID_VALUE }?.let {
            updateTitleTextColor(it)
        }
        navigation.backIconId.takeIf { it != NavigationInformation.NOT_HANDLE_RESOURCE_ID_VALUE }?.let {
            updateBackIcon(it)
        }
        navigation.backgroundColor.takeIf { it != NavigationInformation.NOT_HANDLE_RESOURCE_ID_VALUE }?.let {
            updateBackgroudColor(it)
        }
    }


    override fun updateTitle(title: String) {
        toolbar.title = title
    }

    override fun updateTitle(titleId: Int) {
        updateTitle(resourceManager.getString(titleId))
    }

    override fun updateBackIcon(iconId: Int) {
        toolbar.setNavigationIcon(iconId)
        toolbar.setNavigationOnClickListener { findActivityByContext(context)?.onBackPressed() }
    }

    override fun updateTitleTextColor(colorId: Int) {
        toolbar.setTitleTextColor(resourceManager.getColor(colorId))
    }

    override fun updateBackgroudColor(colorId: Int) {
        super.updateBackgroudColor(colorId)
        toolbar.setBackgroundResource(colorId)
    }

    override fun setupWith(view: View): View {
        val viewParent = generateParent(view.context)
        viewParent.addView(toolbarContainer.root, ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT))
        viewParent.addView(view, ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT))
        return viewParent
    }

    fun generateParent(context: Context): ViewGroup {
        return FrameLayout(context).apply { layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT) }
    }


}