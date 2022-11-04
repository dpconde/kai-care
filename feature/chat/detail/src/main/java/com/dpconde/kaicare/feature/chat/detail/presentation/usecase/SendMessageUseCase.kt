package com.dpconde.kaicare.feature.chat.detail.presentation.usecase

interface SendMessageUseCase {

    suspend fun send(messageText: String, threadId: String)

}