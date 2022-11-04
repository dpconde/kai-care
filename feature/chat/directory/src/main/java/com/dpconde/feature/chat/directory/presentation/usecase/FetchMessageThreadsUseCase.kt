package com.dpconde.feature.chat.directory.presentation.usecase

import com.dpconde.kaicare.core.commons.domain.ChatThread

interface FetchMessageThreadsUseCase {

    /**
     * Fetch message threads
     * It includes a remote fetch and data sync
     */
    suspend fun fetchMessageThreads(): List<ChatThread>

    /**
     * Fetch local stored message threads
     * It is supposed to be fast
     */
    suspend fun fetchCurrentMessageThreads(): List<ChatThread>

}