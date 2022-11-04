package com.dpconde.kaicare.feature.chat.detail.presentation.usecase

import com.dpconde.kaicare.core.commons.domain.ChatMessage

interface SaveMessagesLocalUseCase {

    suspend fun saveMessagesLocal(chatMessages: List<ChatMessage>)

}