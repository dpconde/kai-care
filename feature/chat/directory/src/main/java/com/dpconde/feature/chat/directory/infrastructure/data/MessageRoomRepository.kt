package com.dpconde.feature.chat.directory.infrastructure.data

import com.dpconde.feature.chat.directory.domain.data.MessageLocalRepository
import com.dpconde.feature.chat.directory.domain.entities.Message
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.dpconde.kaicare.core.persistence.room.dao.MessageDao
import com.dpconde.kaicare.core.persistence.room.dbo.MessageDbo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MessageRoomRepository @Inject constructor(
    private val dao: MessageDao,
    private val messageDboTransformer: DataTransformer<MessageDbo, Message>,
    private val messageTransformer: DataTransformer<Message, MessageDbo>
): MessageLocalRepository {

    override suspend fun fetchByThreadId(threadId: String) =
        withContext(Dispatchers.IO){
            dao.getByThreadId(threadId).map { messageDboTransformer.transform(it) }
        }

    override suspend fun save(messages: List<Message>) =
        withContext(Dispatchers.IO){
            val transformed = messages.map { messageTransformer.transform(it) }
            dao.insertAll(transformed)
        }

}