package com.dpconde.kaicare.feature.login.presentation

import androidx.lifecycle.ViewModel
import com.dpconde.kaicare.feature.login.presentation.usecase.AuthUseCases
import com.dpconde.kaicare.feature.login.presentation.usecase.BiometricUseCases

class LoginViewModel(
    private val authUseCases: AuthUseCases,
    private val biometricUseCases: BiometricUseCases
) : ViewModel() {


}