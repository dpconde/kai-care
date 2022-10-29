package com.dpconde.feature.chat.directory.domain.data.local

import com.dpconde.feature.chat.directory.domain.entities.MessageThread

interface MessageThreadLocalRepository{

    suspend fun fetchAll(): List<MessageThread>

    suspend fun save(threads: List<MessageThread>)

}