package com.dpconde.kaicare.feature.login.di


import androidx.fragment.app.FragmentActivity
import com.dpconde.kaicare.core.sensor.biometric.BiometricSensor
import com.dpconde.kaicare.core.sensor.biometric.fingerprint.FingerprintSensor
import com.dpconde.kaicare.core.session.service.SessionManager
import com.dpconde.kaicare.feature.login.domain.authenticator.BiometricAuthenticator
import com.dpconde.kaicare.feature.login.domain.authenticator.EmailPasswordAuthenticator
import com.dpconde.kaicare.feature.login.domain.usecases.AuthUseCasesImpl
import com.dpconde.kaicare.feature.login.domain.usecases.BiometricUseCasesImpl
import com.dpconde.kaicare.feature.login.infrastructure.authenticator.BiometricAuthenticatorImpl
import com.dpconde.kaicare.feature.login.infrastructure.authenticator.EmailPasswordAuthenticatorImpl
import com.dpconde.kaicare.feature.login.presentation.usecase.AuthUseCases
import com.dpconde.kaicare.feature.login.presentation.usecase.BiometricUseCases
import dagger.Module
import dagger.Provides

@Module
class LoginModule {

    @Provides
    fun provideAuthUseCases(
        sessionManager: SessionManager,
        emailPasswordAuthenticator: EmailPasswordAuthenticator
    ): AuthUseCases = AuthUseCasesImpl(sessionManager, emailPasswordAuthenticator)

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

    @Provides
    fun provideEmailPasswordAuthenticator(
    ): EmailPasswordAuthenticator = EmailPasswordAuthenticatorImpl()



}