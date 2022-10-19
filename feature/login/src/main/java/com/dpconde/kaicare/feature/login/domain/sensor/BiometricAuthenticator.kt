package com.dpconde.kaicare.feature.login.domain.sensor

import com.dpconde.kaicare.core.sensor.SensorStatus
import com.dpconde.kaicare.core.sensor.biometric.BiometricAuthResult

interface BiometricAuthenticator {

    /**
     * Check if the current device has this biometric available
     */
    suspend fun isBiometricAvailable(): SensorStatus

    /**
     * Authenticate with biometric
     */
    suspend fun authenticate(): BiometricAuthResult

}