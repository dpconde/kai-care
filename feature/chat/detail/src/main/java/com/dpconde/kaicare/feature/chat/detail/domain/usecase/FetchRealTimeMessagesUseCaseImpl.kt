package com.dpconde.kaicare.feature.chat.detail.domain.usecase

import com.dpconde.kaicare.core.commons.domain.ChatMessage
import com.dpconde.kaicare.core.localpersistence.repository.ChatThreadLocalRepository
import com.dpconde.kaicare.core.remotepersistence.repository.ChatMessageRemoteRepository
import com.dpconde.kaicare.feature.chat.detail.presentation.usecase.FetchRealTimeMessagesUseCase
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class FetchRealTimeMessagesUseCaseImpl @Inject constructor(
    private val chatMessageRemoteRepository: ChatMessageRemoteRepository,
    private val threadLocalRepository: ChatThreadLocalRepository
): FetchRealTimeMessagesUseCase {

    override suspend fun startListeningForMessages(threadId: String): Flow<List<ChatMessage>> {
        val thread = threadLocalRepository.fetchById(threadId)
        return chatMessageRemoteRepository.getRealTimeByThreadIdAndDate(
            threadId, thread.lastFetch?: Date())
    }

    override suspend fun stopListeningForMessages() {
        TODO("Not yet implemented")
    }
}