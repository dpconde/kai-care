package com.dpconde.kaicare.core.remotepersistence.repository

import com.dpconde.kaicare.core.commons.domain.ChatThread

interface ChatThreadRemoteRepository {

    suspend fun getByUser(userId: String): List<ChatThread>

}