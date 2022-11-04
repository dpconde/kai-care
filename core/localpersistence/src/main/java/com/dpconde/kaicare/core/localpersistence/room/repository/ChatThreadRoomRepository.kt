package com.dpconde.kaicare.core.localpersistence.room.repository

import com.dpconde.kaicare.core.commons.domain.ChatThread
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.dpconde.kaicare.core.localpersistence.repository.ChatThreadLocalRepository
import com.dpconde.kaicare.core.localpersistence.room.dao.MessageThreadDao
import com.dpconde.kaicare.core.localpersistence.room.dbo.MessageThreadDbo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class ChatThreadRoomRepository @Inject constructor(
    private val dao: MessageThreadDao,
    private val dboToThreadTransformer: DataTransformer<MessageThreadDbo, ChatThread>,
    private val threadToDboTransformer: DataTransformer<ChatThread, MessageThreadDbo>
): ChatThreadLocalRepository {

    override suspend fun fetchById(threadId: String) = withContext(Dispatchers.IO){
        dboToThreadTransformer.transform(dao.getById(threadId))
    }

    override suspend fun updateLastFetchData(threadId: String, date: Date) = withContext(Dispatchers.IO){
        dao.updateLastFetchDate(threadId, date)
    }

    override suspend fun resetUnreadMessages(threadId: String) = withContext(Dispatchers.IO){
        dao.resetUnreadMessagesByThreadId(threadId)
    }

    override suspend fun fetchAll() = withContext(Dispatchers.IO){
        dao.getAll().map { dboToThreadTransformer.transform(it) }
    }

    override suspend fun save(threads: List<ChatThread>) = withContext(Dispatchers.IO){
        val transformed = threads.map { threadToDboTransformer.transform(it) }
        dao.insertAll(transformed)
    }
}