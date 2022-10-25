package com.dpconde.feature.chat.directory.infrastructure.datatransformer

import com.dpconde.feature.chat.directory.domain.entities.Message
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.dpconde.kaicare.core.persistence.room.dbo.MessageDbo

class MessageDboTransformer: DataTransformer<Message, MessageDbo> {

    override fun transform(from: Message): MessageDbo {
        return MessageDbo(
            from.id,
            from.threadId,
            from.textMessage,
            from.sent,
            from.seen)
    }
}