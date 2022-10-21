package com.dpconde.feature.chat.directory.di

import com.dpconde.feature.chat.directory.presentation.ChatDirectoryFragment
import com.dpconde.kaicare.AppDependencies
import dagger.hilt.android.EntryPointAccessors

internal fun ChatDirectoryFragment.inject() {
    DaggerChatDirectoryUIComponent.builder()
        .context(requireContext())
        .appDependencies(
            EntryPointAccessors.fromApplication(
                requireContext(),
                AppDependencies::class.java
            )
        )
        .build()
        .inject(this)
}