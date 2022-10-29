package com.dpconde.feature.chat.directory.infrastructure.data.local

import com.dpconde.feature.chat.directory.domain.data.local.MessageThreadLocalRepository
import com.dpconde.feature.chat.directory.domain.entities.MessageThread
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.dpconde.kaicare.core.persistence.room.dao.MessageThreadDao
import com.dpconde.kaicare.core.persistence.room.dbo.MessageThreadDbo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MessageThreadRoomRepository @Inject constructor(
    private val dao: MessageThreadDao,
    private val threadDboTransformer: DataTransformer<MessageThreadDbo, MessageThread>,
    private val threadTransformer: DataTransformer<MessageThread, MessageThreadDbo>
): MessageThreadLocalRepository {

    override suspend fun fetchAll(): List<MessageThread> =
        withContext(Dispatchers.IO){
            dao.getAll().map { threadDboTransformer.transform(it) }
        }

    override suspend fun save(threads: List<MessageThread>) =
        withContext(Dispatchers.IO){
            val transformed = threads.map { threadTransformer.transform(it) }
            dao.insertAll(transformed)
        }

}