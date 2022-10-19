package com.dpconde.kaicare.feature.login.di


import androidx.fragment.app.FragmentActivity
import com.dpconde.kaicare.core.sensor.biometric.BiometricSensor
import com.dpconde.kaicare.core.sensor.biometric.fingerprint.FingerprintSensor
import com.dpconde.kaicare.feature.login.domain.sensor.BiometricAuthenticator
import com.dpconde.kaicare.feature.login.domain.usecases.AuthUseCasesImpl
import com.dpconde.kaicare.feature.login.domain.usecases.BiometricUseCasesImpl
import com.dpconde.kaicare.feature.login.infrastructure.biometric.BiometricAuthenticatorImpl
import com.dpconde.kaicare.feature.login.presentation.usecase.AuthUseCases
import com.dpconde.kaicare.feature.login.presentation.usecase.BiometricUseCases
import dagger.Module
import dagger.Provides

@Module
class LoginModule {

    @Provides
    fun provideAuthUseCases(
    ): AuthUseCases = AuthUseCasesImpl()

    @Provides
    fun provideBlogRepository(
        biometricAuthenticator: BiometricAuthenticator,
    ): BiometricUseCases = BiometricUseCasesImpl(biometricAuthenticator)

    @Provides
    fun provideBiometricAuthenticator(
        biometricSensor: BiometricSensor,
    ): BiometricAuthenticator = BiometricAuthenticatorImpl(biometricSensor)

    @Provides
    fun provideBiometricSensor(
        activity: FragmentActivity
    ): BiometricSensor = FingerprintSensor(activity)

}