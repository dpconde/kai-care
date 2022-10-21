package com.dpconde.kaicare.feature.login.domain.usecases

import com.dpconde.kaicare.core.session.service.SessionManager
import com.dpconde.kaicare.feature.login.domain.authenticator.EmailPasswordAuthenticator
import com.dpconde.kaicare.feature.login.presentation.usecase.AuthUseCases
import javax.inject.Inject

class AuthUseCasesImpl @Inject constructor(
    private val sessionManager: SessionManager,
    private val emailPasswordAuthenticator: EmailPasswordAuthenticator
): AuthUseCases {

    override suspend fun doLogin(email: String, password: String) =
        emailPasswordAuthenticator.authenticate(email, password)

    override fun getSessionEmail() = sessionManager.getSessionEmail()

    override fun saveTokenSession(token: String) = sessionManager.saveSessionToken(token)

    override fun saveEmailSession(token: String) = sessionManager.saveSessionEmail(token)

    override fun isSessionAvailable() = !sessionManager.getSessionToken().isNullOrBlank()


}