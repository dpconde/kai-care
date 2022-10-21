package com.dpconde.kaicare.core.session.service

interface SessionManager {

    /**
     * Get session token
     */
    fun getSessionToken(): String?

    /**
     * Save token session
     */
    fun saveSessionToken(token: String)

    /**
     * Get email session
     */
    fun getSessionEmail(): String?

    /**
     * Save email session
     */
    fun saveSessionEmail(email: String)

    /**
     * Clear session data
     */
    fun clearSessionData()
}