package com.dpconde.kaicare.feature.login.infrastructure.biometric

import com.dpconde.kaicare.core.sensor.biometric.BiometricSensor
import com.dpconde.kaicare.feature.login.domain.sensor.BiometricAuthenticator

class BiometricAuthenticatorImpl (
    private val biometricSensor: BiometricSensor): BiometricAuthenticator {

    override suspend fun isBiometricAvailable() = biometricSensor.getStatus()

    override suspend fun authenticate() = biometricSensor.authenticate()

}