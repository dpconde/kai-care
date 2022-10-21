package com.dpconde.kaicare.feature.login.domain.authenticator

import com.dpconde.kaicare.feature.login.domain.entities.AuthResult

interface EmailPasswordAuthenticator {

    /**
     * Authenticate with email and password
     */
    suspend fun authenticate(email: String, password: String): AuthResult

}