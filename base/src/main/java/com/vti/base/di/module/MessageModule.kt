package com.vti.base.di.module

import com.vti.base.message.MessageManager
import com.vti.base.message.MessageManagerImpl
import com.vti.base.view.dialog.message.choice.single.DefaultSingleChoiceViewModel
import com.vti.base.view.dialog.message.normal.DefaultMessageViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val messageModule = module {
    single<MessageManager> { MessageManagerImpl() }
    viewModel { DefaultMessageViewModel() }
    viewModel { DefaultSingleChoiceViewModel() }
}