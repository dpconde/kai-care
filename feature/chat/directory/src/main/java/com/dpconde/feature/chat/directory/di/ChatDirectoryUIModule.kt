package com.dpconde.feature.chat.directory.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dpconde.feature.chat.directory.presentation.ChatDirectoryViewModel
import com.dpconde.kaicare.core.commons.di.ViewModelFactory
import com.dpconde.kaicare.core.commons.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ChatDirectoryUIModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ChatDirectoryViewModel::class)
    internal abstract fun chatDirectoryViewModel(viewModel: ChatDirectoryViewModel): ViewModel

}