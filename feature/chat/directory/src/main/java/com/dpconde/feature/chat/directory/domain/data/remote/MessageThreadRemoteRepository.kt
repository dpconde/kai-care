package com.dpconde.feature.chat.directory.domain.data.remote

import com.dpconde.feature.chat.directory.domain.entities.Message
import com.dpconde.feature.chat.directory.domain.entities.MessageThread
import java.util.*

interface MessageThreadRemoteRepository{

    /**
     * Get all user message threads
     */
    suspend fun getMessageThreadsByUser(userId: String): List<MessageThread>

    /**
     * Get messages given a date and thread id
     */
    suspend fun getMessagesByThreadIdAndDate(
        messageThreadId: String, fromDate: Date): List<Message>
}