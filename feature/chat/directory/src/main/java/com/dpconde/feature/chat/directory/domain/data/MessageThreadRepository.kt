package com.dpconde.feature.chat.directory.domain.data

import com.dpconde.feature.chat.directory.domain.entities.Message
import com.dpconde.feature.chat.directory.domain.entities.MessageThread
import java.util.*

interface MessageThreadRepository{

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