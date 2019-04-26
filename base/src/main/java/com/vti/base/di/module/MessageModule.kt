package com.vti.base.di.module

import com.vti.base.message.MessageManager
import com.vti.base.message.MessageManagerImpl
import com.vti.base.view.dialog.message.DefaultMessageViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val messageModule = module {
    single<MessageManager> { MessageManagerImpl() }
    viewModel { DefaultMessageViewModel() }
}