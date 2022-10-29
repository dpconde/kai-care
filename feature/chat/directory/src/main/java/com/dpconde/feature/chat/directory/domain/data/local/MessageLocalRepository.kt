package com.dpconde.feature.chat.directory.domain.data.local

import com.dpconde.feature.chat.directory.domain.entities.Message

interface MessageLocalRepository{

    suspend fun fetchByThreadId(threadId: String): List<Message>

    suspend fun save(messages: List<Message>)

}