package com.dpconde.kaicare.feature.chat.detail.domain.usecase

import com.dpconde.kaicare.core.commons.domain.ChatMessage
import com.dpconde.kaicare.core.localpersistence.repository.ChatMessageLocalRepository
import com.dpconde.kaicare.feature.chat.detail.presentation.usecase.SaveMessagesLocalUseCase
import javax.inject.Inject

class SaveMessagesLocalUseCaseImpl @Inject constructor(
    private val chatMessageLocalRepository: ChatMessageLocalRepository
): SaveMessagesLocalUseCase {

    override suspend fun saveMessagesLocal(chatMessages: List<ChatMessage>) {
        chatMessageLocalRepository.saveAll(chatMessages)
    }
}