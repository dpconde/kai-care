package com.dpconde.kaicare.feature.login.domain.entities

sealed class AuthResult {

    data class Success(val token: String) : AuthResult()

    data class Error(val errorCode: Int, val errorDesc: String) : AuthResult()
}
