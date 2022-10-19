package com.dpconde.kaicare.core.sensor.biometric

import com.dpconde.kaicare.core.sensor.DeviceSensor

interface BiometricSensor: DeviceSensor {

    suspend fun authenticate(): BiometricAuthResult

}