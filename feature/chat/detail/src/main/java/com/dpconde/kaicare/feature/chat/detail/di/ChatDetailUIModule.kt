package com.dpconde.kaicare.feature.chat.detail.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dpconde.kaicare.core.commons.di.ViewModelFactory
import com.dpconde.kaicare.core.commons.di.ViewModelKey
import com.dpconde.kaicare.feature.chat.detail.presentation.ChatDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ChatDetailUIModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ChatDetailViewModel::class)
    internal abstract fun chatDetailViewModel(viewModel: ChatDetailViewModel): ViewModel

}