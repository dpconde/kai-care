package com.dpconde.kaicare.core.localpersistence.room.datatransformer.chatmessage

import com.dpconde.kaicare.core.commons.domain.ChatMessage
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.dpconde.kaicare.core.localpersistence.room.dbo.MessageDbo

class MessageToDboDataTransformer: DataTransformer<ChatMessage, MessageDbo> {

    override fun transform(from: ChatMessage) =
        MessageDbo(
            from.id,
            from.threadId,
            from.text,
            from.fromUserId,
            from.sentDate,
            null
        )
}