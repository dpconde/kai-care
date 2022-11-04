package com.dpconde.kaicare.core.session.service

interface SessionManager {

    /**
     * Get session user id
     */
    fun getSessionUserId(): String?

    /**
     * Save session user id
     */
    fun saveSessionUserId(token: String)

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