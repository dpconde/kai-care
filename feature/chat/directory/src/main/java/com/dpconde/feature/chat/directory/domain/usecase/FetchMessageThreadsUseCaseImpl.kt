package com.dpconde.feature.chat.directory.domain.usecase

import com.dpconde.feature.chat.directory.domain.data.local.MessageThreadLocalRepository
import com.dpconde.feature.chat.directory.domain.data.remote.MessageThreadRemoteRepository
import com.dpconde.feature.chat.directory.domain.data.remote.UserRemoteRepository
import com.dpconde.feature.chat.directory.domain.entities.MessageThread
import com.dpconde.feature.chat.directory.domain.service.DataSyncService
import com.dpconde.feature.chat.directory.presentation.usecase.FetchMessageThreadsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.util.*
import javax.inject.Inject

class FetchMessageThreadsUseCaseImpl @Inject constructor(
    private val remoteThreadRepository: MessageThreadRemoteRepository,
    private val remoteUserRepository: UserRemoteRepository,
    private val localThreadRepository: MessageThreadLocalRepository,
    private val dataSyncService: DataSyncService,
): FetchMessageThreadsUseCase {

    override suspend fun fetchMessageThreads(): List<MessageThread>{
        return coroutineScope {
            val remoteUsers = async { fetchRemoteUsers() }
            val remoteData = async{ fetchRemoteMessageThreads() }
            dataSyncService.syncData(remoteData.await(), remoteUsers.await())
            fetchCurrentMessageThreads()
        }
    }

    private suspend fun fetchRemoteMessageThreads(): List<MessageThread> {
        val remoteData = remoteThreadRepository.getMessageThreadsByUser("5g6xDmo3QQTxRswoQli6") //TODO
        coroutineScope {
            remoteData.map { messageThread ->
                async(Dispatchers.IO){
                    val messages = remoteThreadRepository.getMessagesByThreadIdAndDate(messageThread.id, Date())
                    messageThread.unprocessedMessages = messages
                }
            }.awaitAll()
        }

        return remoteData
    }

    override suspend fun fetchCurrentMessageThreads() = localThreadRepository.fetchAll()

    private suspend fun fetchRemoteUsers() = remoteUserRepository.getAllUsers()

}