package com.dpconde.kaicare.core.remotepersistence.repository

import com.dpconde.kaicare.core.commons.domain.ChatMessage
import kotlinx.coroutines.flow.Flow
import java.util.*

interface ChatMessageRemoteRepository {

    suspend fun getByThreadIdAndDate(messageThreadId: String, fromDate: Date): List<ChatMessage>

    suspend fun getRealTimeByThreadIdAndDate(messageThreadId: String, fromDate: Date): Flow<List<ChatMessage>>

    suspend fun saveMessage(chatMessage: ChatMessage)
}