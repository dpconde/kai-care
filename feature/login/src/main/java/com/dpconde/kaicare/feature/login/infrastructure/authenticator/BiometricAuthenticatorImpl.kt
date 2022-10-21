package com.dpconde.kaicare.feature.login.infrastructure.authenticator

import com.dpconde.kaicare.core.sensor.biometric.BiometricSensor
import com.dpconde.kaicare.feature.login.domain.authenticator.BiometricAuthenticator
import javax.inject.Inject

class BiometricAuthenticatorImpl @Inject constructor(
    private val biometricSensor: BiometricSensor): BiometricAuthenticator {

    override suspend fun isBiometricAvailable() = biometricSensor.getStatus()

    override suspend fun authenticate() = biometricSensor.authenticate()

}