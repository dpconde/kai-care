package com.dpconde.kaicare.core.sensor.biometric

sealed class BiometricAuthResult {

    object Success : BiometricAuthResult()

    object Failure : BiometricAuthResult()

    data class Error(val errorCode: Int, val errorDesc: String) : BiometricAuthResult()

}