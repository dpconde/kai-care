package com.dpconde.kaicare.feature.login.presentation.usecase

import com.dpconde.kaicare.feature.login.domain.entities.AuthResult
import com.dpconde.kaicare.feature.login.domain.entities.AuthUser

interface AuthUseCases {

    /**
     * Do Login with email and password
     */
    suspend fun doLogin(email: String, password: String): AuthResult

    /**
     * Get current User
     */
    fun getLoggedUser(): AuthUser?

    /**
     * Get user email
     */
    fun getSessionEmail(): String?

    /**
     * Save user id
     */
    fun saveUserId(userId: String)

    /**
     * Save email session
     */
    fun saveEmailSession(token: String)

}