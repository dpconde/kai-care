package com.dpconde.kaicare.core.sensor.biometric.fingerprint

import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricManager.from
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.dpconde.kaicare.core.sensor.SensorStatus
import com.dpconde.kaicare.core.sensor.biometric.BiometricAuthResult
import com.dpconde.kaicare.core.sensor.biometric.BiometricSensor
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.Executor
import kotlin.coroutines.resume

class FingerprintSensor (
    private val activity: AppCompatActivity
): BiometricSensor {

    private var executor: Executor = ContextCompat.getMainExecutor(activity)
    private lateinit var biometricPrompt: BiometricPrompt

    override suspend fun authenticate(): BiometricAuthResult =
        suspendCancellableCoroutine { cont ->
            val callback = object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    cont.resume(BiometricAuthResult.Error(errorCode, errString.toString()))
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    cont.resume(BiometricAuthResult.Success)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    cont.resume(BiometricAuthResult.Failure)
                }
            }

            biometricPrompt = BiometricPrompt(activity, executor, callback)
            biometricPrompt.authenticate(createPrompt())

        }

    private fun createPrompt() = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Biometric login for my app")
        .setSubtitle("Log in using your biometric credential")
        .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
        .build()

    override fun getStatus(): SensorStatus {
        val biometricManager = from(activity)
        return when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> SensorStatus.AVAILABLE
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> SensorStatus.NOT_SUPPORTED
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> SensorStatus.NOT_AVAILABLE
            else -> SensorStatus.NOT_SUPPORTED
        }
    }

}