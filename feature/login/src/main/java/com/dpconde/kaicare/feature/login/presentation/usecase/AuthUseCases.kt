package com.dpconde.kaicare.feature.login.presentation.usecase

import com.dpconde.kaicare.feature.login.domain.entities.AuthResult

interface AuthUseCases {

    /**
     * Do Login with email and password
     */
    suspend fun doLogin(email: String, password: String): AuthResult

    /**
     * Check if there is a session available
     */
    fun isSessionAvailable(): Boolean

    /**
     * Get user email
     */
    fun getSessionEmail(): String?

    /**
     * Save token session
     */
    fun saveTokenSession(token: String)

    /**
     * Save email session
     */
    fun saveEmailSession(token: String)

}