package com.dpconde.kaicare.feature.login.domain.authenticator

import com.dpconde.kaicare.feature.login.domain.entities.AuthResult
import com.dpconde.kaicare.feature.login.domain.entities.AuthUser

interface EmailPasswordAuthenticator {

    /**
     * Authenticate with email and password
     */
    suspend fun authenticate(email: String, password: String): AuthResult

    /**
     * Get logged user. If null, user is not actually logged
     */
    fun getLoggedUser(): AuthUser?



}