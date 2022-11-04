package com.dpconde.kaicare.feature.chat.detail.di

import com.dpconde.kaicare.AppDependencies
import com.dpconde.kaicare.feature.chat.detail.presentation.ChatDetailFragment
import dagger.hilt.android.EntryPointAccessors

internal fun ChatDetailFragment.inject() {
    DaggerChatDetailUIComponent.builder()
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