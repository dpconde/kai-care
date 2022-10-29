package com.dpconde.feature.chat.directory.infrastructure.datatransformer.thread

import com.dpconde.feature.chat.directory.domain.entities.MessageThread
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.dpconde.kaicare.core.persistence.room.dbo.MessageThreadDbo

class MessageThreadDboTransformer: DataTransformer<MessageThread, MessageThreadDbo> {

    override fun transform(from: MessageThread): MessageThreadDbo {
        return MessageThreadDbo(
            from.id,
            from.lastFetch,
            from.unreadMessages ?: 0,
            from.lastMessage,
            from.lastMessageDate,
            from.isGroup,
            from.name,
            from.role,
            from.imageUrl
        )
    }
}