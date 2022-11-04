package com.dpconde.feature.chat.directory.domain.usecase

import com.dpconde.feature.chat.directory.domain.service.DataSyncService
import com.dpconde.feature.chat.directory.presentation.usecase.FetchMessageThreadsUseCase
import com.dpconde.kaicare.core.commons.domain.ChatThread
import com.dpconde.kaicare.core.localpersistence.repository.ChatThreadLocalRepository
import com.dpconde.kaicare.core.remotepersistence.repository.ChatMessageRemoteRepository
import com.dpconde.kaicare.core.remotepersistence.repository.ChatThreadRemoteRepository
import com.dpconde.kaicare.core.remotepersistence.repository.UserRemoteRepository
import com.dpconde.kaicare.core.session.service.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.util.*
import javax.inject.Inject

class FetchMessageThreadsUseCaseImpl @Inject constructor(
    private val chatMessageRemoteRepository: ChatMessageRemoteRepository,
    private val userRemoteRepository: UserRemoteRepository,
    private val chatThreadRemoteRepository: ChatThreadRemoteRepository,
    private val chatThreadLocalRepository: ChatThreadLocalRepository,
    private val dataSyncService: DataSyncService,
    private val sessionManager: SessionManager
): FetchMessageThreadsUseCase {

    override suspend fun fetchMessageThreads(): List<ChatThread>{
        return coroutineScope {
            val remoteUsers = async { fetchRemoteUsers() }
            val remoteData = async{ fetchRemoteMessageThreadsAndMessages() }
            val localData = async{ chatThreadLocalRepository.fetchAll()}
            dataSyncService.syncData(localData.await(), remoteData.await(), remoteUsers.await())
            fetchCurrentMessageThreads()
        }
    }

    private suspend fun fetchRemoteMessageThreadsAndMessages(): List<ChatThread> {
        val localData = chatThreadLocalRepository.fetchAll()
        val remoteData = chatThreadRemoteRepository.getByUser(sessionManager.getSessionUserId()!!)
        coroutineScope {
            remoteData.map { messageThread ->
                async(Dispatchers.IO){
                    val messages = chatMessageRemoteRepository.getByThreadIdAndDate(
                        messageThread.id,
                        localData.firstOrNull() { messageThread.id == it.id }?.lastFetch ?: Date())
                    messageThread.unprocessedMessages = messages
                }
            }.awaitAll()
        }

        return remoteData
    }

    override suspend fun fetchCurrentMessageThreads() = chatThreadLocalRepository.fetchAll()

    private suspend fun fetchRemoteUsers() = userRemoteRepository.getAllUsers()

}