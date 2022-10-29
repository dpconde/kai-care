package com.dpconde.feature.chat.directory.domain.service

import com.dpconde.feature.chat.directory.domain.data.local.MessageLocalRepository
import com.dpconde.feature.chat.directory.domain.data.local.MessageThreadLocalRepository
import com.dpconde.feature.chat.directory.domain.data.local.UserLocalRepository
import com.dpconde.feature.chat.directory.domain.entities.MessageThread
import com.dpconde.feature.chat.directory.domain.entities.User
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.util.*
import javax.inject.Inject

class DataSyncService @Inject constructor(
    private val localUserRepository: UserLocalRepository,
    private val localMessageRepository: MessageLocalRepository,
    private val localThreadRepository: MessageThreadLocalRepository,
    //TODO Algo para obtener el id de usuario loggeado
){

    suspend fun syncData(remote: List<MessageThread>, remoteUsers: List<User>){
        coroutineScope {
            awaitAll(

                //Sync users
                async {
                    remoteUsers.apply { localUserRepository.save(this) }
                },

                //Sync non existing threads
                async { remote
                    .map { updateLastFetch(it) }
                    .map { updateUserThreadInfo(it, remoteUsers) }
                    .apply { localThreadRepository.save(this) }
                },

                //Update existing threads: Update last fetch date, unread messages
                async { remote
                    .map { updateUnreadMessages(it, it.unprocessedMessages?.size ?: 0) }
                    .map { updateLastFetch(it) }
                    .map { updateLastMessage(it) }
                    .map { updateLastMessageDate(it) }
                    .apply { saveLocalMessageThreads(this) }
                    .forEach { saveUnprocessedMessages(it) }
                }
            )
        }
    }

    private fun updateLastMessage(messageThread: MessageThread): MessageThread {
        messageThread.lastMessage = when(messageThread.unprocessedMessages.isNullOrEmpty()){
            true -> ""
            false -> messageThread.unprocessedMessages!!.last().textMessage
        }
        return messageThread
    }

    private fun updateLastMessageDate(messageThread: MessageThread): MessageThread {
        messageThread.lastMessageDate = when(messageThread.unprocessedMessages.isNullOrEmpty()){
            true -> null
            false -> messageThread.unprocessedMessages!!.last().sent
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

    private fun updateUserThreadInfo(messageThread: MessageThread, remoteUsers: List<User>) : MessageThread {
        return messageThread.apply {
            val threadOppositeUser = remoteUsers.first {
                it.id == messageThread.members
                    .filterNot { threadUserId -> threadUserId == "5g6xDmo3QQTxRswoQli6" } //TODO
                    .first().toString()
            }

            this.name = threadOppositeUser.name
            this.imageUrl = threadOppositeUser.imageUrl
            this.role = threadOppositeUser.role
        }
    }

}