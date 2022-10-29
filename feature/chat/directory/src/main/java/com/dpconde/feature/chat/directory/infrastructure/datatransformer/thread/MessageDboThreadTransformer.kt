package com.dpconde.feature.chat.directory.infrastructure.datatransformer.thread

import com.dpconde.feature.chat.directory.domain.entities.MessageThread
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.dpconde.kaicare.core.persistence.room.dbo.MessageThreadDbo

class MessageDboThreadTransformer: DataTransformer<MessageThreadDbo, MessageThread> {

    override fun transform(from: MessageThreadDbo): MessageThread {
        return MessageThread(
            from.id,
            from.isGroup,
            from.lastFetch,
            listOf(),//TODO
            from.lastMessage,
            from.lastMessageDate,
            null,
            from.unreadMessages,
            from.name,
            from.userProfile ?: "",
            from.imageUrl
        )
    }
}