package com.dpconde.kaicare.core.localpersistence.repository

import com.dpconde.kaicare.core.commons.domain.ChatMessage

interface ChatMessageLocalRepository {

    suspend fun fetchByThreadId(threadId: String): List<ChatMessage>

    suspend fun saveAll(chatMessages: List<ChatMessage>)

    suspend fun setAllAsRead(threadId: String)

}