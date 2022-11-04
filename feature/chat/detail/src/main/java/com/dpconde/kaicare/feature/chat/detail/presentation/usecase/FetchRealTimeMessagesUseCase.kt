package com.dpconde.kaicare.feature.chat.detail.presentation.usecase

import com.dpconde.kaicare.core.commons.domain.ChatMessage
import kotlinx.coroutines.flow.Flow

interface FetchRealTimeMessagesUseCase {

    suspend fun startListeningForMessages(threadId: String): Flow<List<ChatMessage>>

    suspend fun stopListeningForMessages()
}