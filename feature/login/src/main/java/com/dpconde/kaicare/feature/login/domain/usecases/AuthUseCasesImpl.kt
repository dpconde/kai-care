package com.dpconde.kaicare.feature.login.domain.usecases

import com.dpconde.kaicare.feature.login.presentation.usecase.AuthUseCases

class AuthUseCasesImpl: AuthUseCases {

    override suspend fun doLogin(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun isSessionAvailable(): Boolean {
        TODO("Not yet implemented")
    }
}