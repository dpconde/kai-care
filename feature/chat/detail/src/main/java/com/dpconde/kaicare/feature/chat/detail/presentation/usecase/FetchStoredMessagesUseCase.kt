package com.dpconde.kaicare.feature.chat.detail.presentation.usecase

import com.dpconde.kaicare.core.commons.domain.ChatMessage

interface FetchStoredMessagesUseCase {

    suspend fun fetchAllMessagesByThreadId(threadId: String): List<ChatMessage>

}