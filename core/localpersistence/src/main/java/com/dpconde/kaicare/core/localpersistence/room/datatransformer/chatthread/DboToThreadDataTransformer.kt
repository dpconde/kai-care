package com.dpconde.kaicare.core.localpersistence.room.datatransformer.chatthread

import com.dpconde.kaicare.core.commons.domain.ChatThread
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.dpconde.kaicare.core.localpersistence.room.dbo.MessageThreadDbo

class DboToThreadDataTransformer: DataTransformer<MessageThreadDbo, ChatThread> {

    override fun transform(from: MessageThreadDbo) =
        ChatThread(
            from.id,
            from.lastFetch!!,
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