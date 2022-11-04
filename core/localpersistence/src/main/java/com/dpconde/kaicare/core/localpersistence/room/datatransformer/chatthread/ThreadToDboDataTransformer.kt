package com.dpconde.kaicare.core.localpersistence.room.datatransformer.chatthread

import com.dpconde.kaicare.core.commons.domain.ChatThread
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.dpconde.kaicare.core.localpersistence.room.dbo.MessageThreadDbo

class ThreadToDboDataTransformer: DataTransformer<ChatThread, MessageThreadDbo> {

    override fun transform(from: ChatThread) =
        MessageThreadDbo(
            from.id,
            from.lastFetch,
            from.unreadMessages ?: 0,
            from.lastMessage,
            from.lastMessageDate,
            from.name,
            from.role,
            from.imageUrl
        )
}