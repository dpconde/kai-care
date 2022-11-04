package com.dpconde.kaicare.feature.chat.detail.domain.usecase

import com.dpconde.kaicare.core.commons.domain.ChatMessage
import com.dpconde.kaicare.core.localpersistence.repository.ChatMessageLocalRepository
import com.dpconde.kaicare.feature.chat.detail.presentation.usecase.FetchStoredMessagesUseCase
import javax.inject.Inject

class FetchStoredMessagesUseCaseImpl @Inject constructor(
    private val chatMessageLocalRepository: ChatMessageLocalRepository
): FetchStoredMessagesUseCase {

    override suspend fun fetchAllMessagesByThreadId(threadId: String): List<ChatMessage> {
        return chatMessageLocalRepository.fetchByThreadId(threadId)
    }
}