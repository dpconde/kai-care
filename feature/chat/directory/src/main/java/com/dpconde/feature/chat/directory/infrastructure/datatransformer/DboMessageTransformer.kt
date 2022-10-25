package com.dpconde.feature.chat.directory.infrastructure.datatransformer

import com.dpconde.feature.chat.directory.domain.entities.Message
import com.dpconde.feature.chat.directory.domain.entities.MessageType
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.dpconde.kaicare.core.persistence.room.dbo.MessageDbo

class DboMessageTransformer: DataTransformer<MessageDbo, Message> {

    override fun transform(from: MessageDbo): Message {
        return Message(
            from.id,
            from.threadId,
            MessageType.TEXT,
            from.textMessage,
            from.sent,
            from.seen)
    }
}