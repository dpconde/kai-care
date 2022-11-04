package com.dpconde.kaicare.core.localpersistence.room.datatransformer.chatmessage

import com.dpconde.kaicare.core.commons.domain.ChatMessage
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.dpconde.kaicare.core.localpersistence.room.dbo.MessageDbo

class DboToMessageDataTransformer: DataTransformer<MessageDbo, ChatMessage> {

    override fun transform(from: MessageDbo) =
        ChatMessage(from.id, from.textMessage, from.from, from.sent, from.threadId)
}