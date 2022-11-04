package com.dpconde.kaicare.feature.chat.detail.domain.usecase

import com.dpconde.kaicare.core.commons.domain.ChatMessage
import com.dpconde.kaicare.core.localpersistence.repository.ChatMessageLocalRepository
import com.dpconde.kaicare.core.remotepersistence.repository.ChatMessageRemoteRepository
import com.dpconde.kaicare.core.session.service.SessionManager
import com.dpconde.kaicare.feature.chat.detail.presentation.usecase.SendMessageUseCase
import java.util.*
import javax.inject.Inject

class SendMessageUseCaseImpl @Inject constructor(
    private val chatMessageLocalRepository: ChatMessageLocalRepository,
    private val chatMessageRemoteRepository: ChatMessageRemoteRepository,
    private val sessionManager: SessionManager
): SendMessageUseCase {

    override suspend fun send(messageText: String, threadId: String) {
        val message = createMessage(messageText, threadId)
        //localMessageRepository.saveMessages(listOf(message))
        chatMessageRemoteRepository.saveMessage(message)
    }

    private fun createMessage(textMessage: String, threadId: String) =
        ChatMessage(
            id = UUID.randomUUID().toString(),
            text = textMessage,
            fromUserId = sessionManager.getSessionUserId()!!,
            sentDate = Date(),
            threadId = threadId
        )
}