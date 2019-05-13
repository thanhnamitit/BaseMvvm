package com.vti.base.di.module

import com.vti.base.message.DialogFactory
import com.vti.base.message.MessageManager
import com.vti.base.message.impl.DialogFactoryImpl
import com.vti.base.message.impl.MessageManagerImpl
import com.vti.base.navigation.layoutfactory.LayoutFactory
import com.vti.base.navigation.layoutfactory.NavigationLayoutFactory
import com.vti.base.resource.executor.ResourceManagerImpl
import com.vti.base.resource.functional.ResourceManager
import com.vti.base.view.dialog.message.choice.single.DefaultSingleChoiceViewModel
import com.vti.base.view.dialog.message.normal.DefaultMessageViewModel
import com.vti.base.view.dialog.permission.RequestPermissionViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val baseModule = module {
    single<MessageManager> { MessageManagerImpl() }
    single<DialogFactory> { DialogFactoryImpl() }
    single<ResourceManager> { ResourceManagerImpl() }
    single<LayoutFactory> { NavigationLayoutFactory() }

    viewModel { DefaultMessageViewModel() }
    viewModel { DefaultSingleChoiceViewModel() }
    viewModel { RequestPermissionViewModel() }
}
