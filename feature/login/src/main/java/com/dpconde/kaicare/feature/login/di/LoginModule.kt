package com.dpconde.kaicare.feature.login.di


import androidx.fragment.app.FragmentActivity
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.dpconde.kaicare.core.sensor.biometric.BiometricSensor
import com.dpconde.kaicare.core.sensor.biometric.fingerprint.FingerprintSensor
import com.dpconde.kaicare.core.session.service.SessionManager
import com.dpconde.kaicare.feature.login.domain.authenticator.BiometricAuthenticator
import com.dpconde.kaicare.feature.login.domain.authenticator.EmailPasswordAuthenticator
import com.dpconde.kaicare.feature.login.domain.entities.AuthUser
import com.dpconde.kaicare.feature.login.domain.usecases.AuthUseCasesImpl
import com.dpconde.kaicare.feature.login.domain.usecases.BiometricUseCasesImpl
import com.dpconde.kaicare.feature.login.infrastructure.authenticator.BiometricAuthenticatorImpl
import com.dpconde.kaicare.feature.login.infrastructure.authenticator.EmailPasswordAuthenticatorImpl
import com.dpconde.kaicare.feature.login.infrastructure.datatransformer.UserTransformer
import com.dpconde.kaicare.feature.login.presentation.usecase.AuthUseCases
import com.dpconde.kaicare.feature.login.presentation.usecase.BiometricUseCases
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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
        userTransformer: DataTransformer<FirebaseUser, AuthUser>,
        firebaseAuth: FirebaseAuth
    ): EmailPasswordAuthenticator = EmailPasswordAuthenticatorImpl(userTransformer, firebaseAuth)

    @Provides
    fun provideFirebaseAuthenticator(): FirebaseAuth = Firebase.auth

    @Provides
    fun provideUserTransformer(
    ): DataTransformer<FirebaseUser, AuthUser> = UserTransformer()





}