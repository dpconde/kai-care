package com.dpconde.kaicare.feature.login.presentation.usecase

import com.dpconde.kaicare.core.sensor.biometric.BiometricAuthResult

interface BiometricUseCases {

    /**
     * Check if biometric is available
     */
    suspend fun isBiometricAvailable(): Boolean

    /**
     * Authenticate with biometric
     */
    suspend fun authenticateWithBiometric(): BiometricAuthResult

}