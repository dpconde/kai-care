package com.dpconde.kaicare.core.localpersistence.repository

import com.dpconde.kaicare.core.commons.domain.ChatThread
import java.util.*

interface ChatThreadLocalRepository {

    suspend fun fetchById(threadId: String): ChatThread

    suspend fun updateLastFetchData(threadId: String, date: Date)

    suspend fun resetUnreadMessages(threadId: String)

    suspend fun fetchAll(): List<ChatThread>

    suspend fun save(threads: List<ChatThread>)

}