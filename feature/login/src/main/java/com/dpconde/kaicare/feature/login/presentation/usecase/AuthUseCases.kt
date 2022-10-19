package com.dpconde.kaicare.feature.login.presentation.usecase

interface AuthUseCases {

    /**
     * Do Login with email and password
     */
    suspend fun doLogin(email: String, password: String)

    /**
     * Check if there is a session available
     */
    suspend fun isSessionAvailable(): Boolean

}