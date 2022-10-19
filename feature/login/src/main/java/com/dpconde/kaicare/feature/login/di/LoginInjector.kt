package com.dpconde.kaicare.feature.login.di

import com.dpconde.kaicare.AppDependencies
import com.dpconde.kaicare.feature.login.presentation.LoginFragment
import dagger.hilt.android.EntryPointAccessors

internal fun LoginFragment.inject() {
    DaggerLoginUIComponent.builder()
        .context(requireContext())
        .activity(requireActivity())
        .appDependencies(
            EntryPointAccessors.fromApplication(
                requireContext(),
                AppDependencies::class.java
            )
        )
        .build()
        .inject(this)
}