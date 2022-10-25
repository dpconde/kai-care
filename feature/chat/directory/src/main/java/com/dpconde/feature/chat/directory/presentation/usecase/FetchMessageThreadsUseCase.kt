package com.dpconde.feature.chat.directory.presentation.usecase

import com.dpconde.feature.chat.directory.domain.entities.MessageThread

interface FetchMessageThreadsUseCase {

    /**
     * Fetch message threads
     * It includes a remote fetch and data sync
     */
    suspend fun fetchMessageThreads(): List<MessageThread>

    /**
     * Fetch local stored message threads
     * It is supposed to be fast
     */
    suspend fun fetchCurrentMessageThreads(): List<MessageThread>

}