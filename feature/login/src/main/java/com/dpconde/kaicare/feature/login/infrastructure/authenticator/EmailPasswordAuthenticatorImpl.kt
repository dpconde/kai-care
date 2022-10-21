package com.dpconde.kaicare.feature.login.infrastructure.authenticator

import com.dpconde.kaicare.feature.login.domain.authenticator.EmailPasswordAuthenticator
import com.dpconde.kaicare.feature.login.domain.entities.AuthResult
import kotlinx.coroutines.delay
import java.util.*
import javax.inject.Inject

class EmailPasswordAuthenticatorImpl @Inject constructor(): EmailPasswordAuthenticator {

    override suspend fun authenticate(email: String, password: String): AuthResult {
        delay(1000)
        if(email == "test@gmail.com" && password == "dpc1234") return AuthResult.Success(generateToken())
        return AuthResult.Error(1, "Invalid credentials")
    }

    private fun generateToken() = UUID.randomUUID().toString()

}