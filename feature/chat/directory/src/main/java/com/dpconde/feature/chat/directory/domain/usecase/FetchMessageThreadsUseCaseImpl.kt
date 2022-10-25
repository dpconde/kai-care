package com.dpconde.feature.chat.directory.domain.usecase

import com.dpconde.feature.chat.directory.domain.data.MessageLocalRepository
import com.dpconde.feature.chat.directory.domain.data.MessageThreadLocalRepository
import com.dpconde.feature.chat.directory.domain.data.MessageThreadRemoteRepository
import com.dpconde.feature.chat.directory.domain.entities.MessageThread
import com.dpconde.feature.chat.directory.presentation.usecase.FetchMessageThreadsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.util.*
import javax.inject.Inject

class FetchMessageThreadsUseCaseImpl @Inject constructor(
    private val remoteThreadRepository: MessageThreadRemoteRepository,
    private val localThreadRepository: MessageThreadLocalRepository,
    private val localMessageRepository: MessageLocalRepository
    //TODO Algo para obtener el id de usuario loggeado
): FetchMessageThreadsUseCase {

    override suspend fun fetchMessageThreads(): List<MessageThread>{
        coroutineScope {
            val localData = async{ fetchLocalMessageThreads() }
            val remoteData = async{ fetchRemoteMessageThreads() }
            syncData(localData.await(), remoteData.await())
        }

        return fetchLocalMessageThreads()
    }

    override suspend fun fetchCurrentMessageThreads() = fetchLocalMessageThreads()

    private suspend fun fetchLocalMessageThreads() = localThreadRepository.fetchAll()

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

    private suspend fun syncData(local: List<MessageThread>, remote: List<MessageThread>){
        //Sync non existing threads
        remote
            .filterNot { remoteThread ->
                local.map { localThread -> localThread.id }.contains(remoteThread.id) }
            .map{ updateLastFetch(it) }
            .apply { localThreadRepository.save(this) }

        //Update existing threads: Update last fetch date, unread messages
        remote
            .filter { remoteThread ->
                local.map { localThread -> localThread.id }.contains(remoteThread.id) }
            .map { updateUnreadMessages(it, it.unprocessedMessages?.size ?: 0) }
            .map { updateLastFetch(it) }
            .map { updateLastMessage(it) }
            .apply { saveLocalMessageThreads(this) }
            .forEach { saveUnprocessedMessages(it) }
    }

    private fun updateLastMessage(messageThread: MessageThread): MessageThread {
        messageThread.lastMessage = when(messageThread.unprocessedMessages.isNullOrEmpty()){
            true -> ""
            false -> messageThread.unprocessedMessages!!.last().textMessage
        }
        return messageThread
    }

    private suspend fun saveUnprocessedMessages(thread: MessageThread){
        thread.unprocessedMessages?.let {
            localMessageRepository.save(it)
        }
    }

    private suspend fun saveLocalMessageThreads(threads: List<MessageThread>): List<MessageThread> {
        localThreadRepository.save(threads)
        return threads
    }

    private fun updateUnreadMessages(messageThread: MessageThread, number: Int) : MessageThread {
        messageThread.unreadMessages = number
        return messageThread
    }

    private fun updateLastFetch(messageThread: MessageThread) : MessageThread {
        messageThread.lastFetch = Date()
        return messageThread
    }

}