package com.dpconde.feature.chat.directory.presentation.usecase

import com.dpconde.feature.chat.directory.domain.entities.MessageThread

interface FetchMessageThreadsUseCase {

    suspend fun fetchMessageThreads(): List<MessageThread>

}