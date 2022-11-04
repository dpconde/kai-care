package com.dpconde.kaicare.core.localpersistence.room.repository

import com.dpconde.kaicare.core.commons.domain.ChatMessage
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.dpconde.kaicare.core.localpersistence.repository.ChatMessageLocalRepository
import com.dpconde.kaicare.core.localpersistence.room.dao.MessageDao
import com.dpconde.kaicare.core.localpersistence.room.dbo.MessageDbo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChatMessageRoomRepository @Inject constructor(
    private val messageDao: MessageDao,
    private val dboToChatMessageTransformer: DataTransformer<MessageDbo, ChatMessage>,
    private val chatMessageToDboTransformer: DataTransformer<ChatMessage, MessageDbo>
): ChatMessageLocalRepository {

    override suspend fun fetchByThreadId(threadId: String) = withContext(Dispatchers.IO){
        messageDao.getByThreadId(threadId).map { dboToChatMessageTransformer.transform(it) }
    }

    override suspend fun saveAll(chatMessages: List<ChatMessage>) = withContext(Dispatchers.IO){
        messageDao.insertAll(chatMessages.map { chatMessageToDboTransformer.transform(it) })
    }

    override suspend fun setAllAsRead(threadId: String) = withContext(Dispatchers.IO){
        messageDao.setAllAsReadByThreadId(threadId)
    }
}