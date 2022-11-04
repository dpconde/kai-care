package com.dpconde.kaicare.feature.chat.detail.presentation.usecase

interface SetAllLocalMessagesAsReadUseCase {

    suspend fun set(threadId: String)

}