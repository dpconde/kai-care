package com.dpconde.feature.chat.directory.domain.service

import com.dpconde.kaicare.core.commons.domain.ChatThread
import com.dpconde.kaicare.core.commons.domain.User
import com.dpconde.kaicare.core.localpersistence.repository.ChatMessageLocalRepository
import com.dpconde.kaicare.core.localpersistence.repository.ChatThreadLocalRepository
import com.dpconde.kaicare.core.localpersistence.repository.UserLocalRepository
import com.dpconde.kaicare.core.session.service.SessionManager
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.util.*
import javax.inject.Inject

class DataSyncService @Inject constructor(
    private val userLocalRepository: UserLocalRepository,
    private val chatMessageLocalRepository: ChatMessageLocalRepository,
    private val chatThreadLocalRepository: ChatThreadLocalRepository,
    private val sessionManager: SessionManager
){

    suspend fun syncData(localData: List<ChatThread>, remote: List<ChatThread>, remoteUsers: List<User>){
        coroutineScope {
            awaitAll(

                //Sync users
                async {
                    remoteUsers.apply { userLocalRepository.save(this) }
                },

                //Sync non existing threads
                async { remote
                    .filterNot { remote -> localData.map { it.id }.contains(remote.id) }
                    .onEach { remote -> updateLastFetch(
                        remote,  localData.firstOrNull { local -> local.id == remote.id }) }
                    .onEach { remote -> updateUserThreadInfo(remote, remoteUsers) }
                    .onEach { it.lastMessage = "* Still no messages *" }
                    .apply { chatThreadLocalRepository.save(this) }
                },

                //Update existing threads: Update last fetch date, unread messages
                async { remote
                    .filter { remote -> localData.map { it.id }.contains(remote.id) }
                    .onEach { remote ->
                        updateUnreadMessages(
                            remote,
                            localData.firstOrNull { local -> local.id == remote.id })
                    }
                    .onEach { remote -> updateLastFetch(
                        remote,
                        localData.firstOrNull { local -> local.id == remote.id }) }
                    .onEach { remote -> updateUserThreadInfo(remote, remoteUsers) }
                    .onEach { remote -> getLastMessage(
                        remote,
                        localData.firstOrNull { local -> local.id == remote.id })
                    }
                    .onEach { remote ->
                        updateLastMessageDate(
                            remote,
                            localData.firstOrNull { local -> local.id == remote.id })
                    }
                    .apply { saveLocalMessageThreads(this) }
                    .forEach { saveUnprocessedMessages(it) }
                }
            )
        }
    }

    private fun updateUnreadMessages(remoteThread: ChatThread, localThread: ChatThread?) {
        remoteThread.unreadMessages =
                ((remoteThread.unprocessedMessages?.size ?: 0) + (localThread?.unreadMessages ?: 0))
    }

    private suspend fun updateLastFetch(remoteThread: ChatThread, localThread: ChatThread?){
        remoteThread.lastFetch = when(remoteThread.unprocessedMessages.isNullOrEmpty()){
            false -> remoteThread.unprocessedMessages!!.first().sentDate
            true -> localThread?.let {
                chatMessageLocalRepository.fetchByThreadId(localThread.id).lastOrNull()?.sentDate ?: Date()
            }?: Date()
        }
    }

    private suspend fun updateLastMessageDate(remoteThread: ChatThread, localThread: ChatThread?){
        remoteThread.lastMessageDate = when(remoteThread.unprocessedMessages.isNullOrEmpty()){
            false -> remoteThread.unprocessedMessages!!.first().sentDate
            true -> localThread?.let {
                chatMessageLocalRepository.fetchByThreadId(localThread.id).lastOrNull()?.sentDate
            }
        }
    }

    private suspend fun saveUnprocessedMessages(thread: ChatThread){
        thread.unprocessedMessages?.let {
            chatMessageLocalRepository.saveAll(it)
        }
    }

    private suspend fun getLastMessage(remoteThread: ChatThread, localThread: ChatThread?){
        remoteThread.lastMessage = when(remoteThread.unprocessedMessages.isNullOrEmpty()) {
            true -> when (localThread != null) {
                true -> chatMessageLocalRepository.fetchByThreadId(localThread.id)
                        .lastOrNull()?.text?:"* Still no messages *"
                false -> "* Still no messages *"
            }
            false -> remoteThread.unprocessedMessages!!.first().text
        }
    }

    private suspend fun saveLocalMessageThreads(threads: List<ChatThread>): List<ChatThread> {
        chatThreadLocalRepository.save(threads)
        return threads
    }

    private fun updateUserThreadInfo(messageThread: ChatThread, remoteUsers: List<User>) {
        messageThread.apply {
            val threadOppositeUser = remoteUsers.first {
                it.id == messageThread.members
                    .filterNot { threadUserId -> threadUserId == sessionManager.getSessionUserId() }
                    .first().toString()
            }

            this.name = threadOppositeUser.name
            this.imageUrl = threadOppositeUser.imageUrl
            this.role = threadOppositeUser.role
        }
    }

}