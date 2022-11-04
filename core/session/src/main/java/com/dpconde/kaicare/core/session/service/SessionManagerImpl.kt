package com.dpconde.kaicare.core.session.service

import android.content.Context
import com.dpconde.kaicare.core.session.data.SessionKey
import javax.inject.Inject

class SessionManagerImpl @Inject constructor(
    private val context: Context
): SessionManager {

    override fun getSessionUserId() =
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
            .getString(SessionKey.USER_ID.value, null)

    override fun saveSessionUserId(token: String) =
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
            .edit().putString(SessionKey.USER_ID.value, token).apply()

    override fun getSessionEmail() =
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
            .getString(SessionKey.EMAIL.value, null)

    override fun saveSessionEmail(email: String) =
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
            .edit().putString(SessionKey.EMAIL.value, email).apply()

    override fun clearSessionData() = SessionKey.values().forEach { clearValue(it.value) }

    private fun clearValue(key: String) =
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
            .edit().remove(key).apply()
}