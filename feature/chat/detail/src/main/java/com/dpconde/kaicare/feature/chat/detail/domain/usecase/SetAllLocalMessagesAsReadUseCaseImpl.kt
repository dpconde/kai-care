package com.dpconde.kaicare.feature.chat.detail.domain.usecase

import com.dpconde.kaicare.core.localpersistence.repository.ChatMessageLocalRepository
import com.dpconde.kaicare.core.localpersistence.repository.ChatThreadLocalRepository
import com.dpconde.kaicare.feature.chat.detail.presentation.usecase.SetAllLocalMessagesAsReadUseCase
import javax.inject.Inject

class SetAllLocalMessagesAsReadUseCaseImpl @Inject constructor(
    private val chatMessageLocalRepository: ChatMessageLocalRepository,
    private val chatThreadLocalRepository: ChatThreadLocalRepository
): SetAllLocalMessagesAsReadUseCase {

    override suspend fun set(threadId: String) {
        chatMessageLocalRepository.setAllAsRead(threadId)
        chatThreadLocalRepository.resetUnreadMessages(threadId)
    }
}