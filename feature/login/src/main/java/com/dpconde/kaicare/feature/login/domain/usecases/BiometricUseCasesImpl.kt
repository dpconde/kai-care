package com.dpconde.kaicare.feature.login.domain.usecases

import com.dpconde.kaicare.core.sensor.SensorStatus
import com.dpconde.kaicare.feature.login.domain.sensor.BiometricAuthenticator
import com.dpconde.kaicare.feature.login.presentation.usecase.BiometricUseCases
import javax.inject.Inject

class BiometricUseCasesImpl @Inject constructor(
    private val biometricAuthenticator: BiometricAuthenticator
): BiometricUseCases {

    override suspend fun isBiometricAvailable() =
        biometricAuthenticator.isBiometricAvailable() == SensorStatus.AVAILABLE

    override suspend fun authenticateWithBiometric() =
        biometricAuthenticator.authenticate()
}