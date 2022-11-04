package com.dpconde.kaicare.core.remotepersistence.firestore.datatransformer.chatmessage

import com.dpconde.kaicare.core.commons.domain.ChatMessage
import com.dpconde.kaicare.core.commons.service.DataTransformer

class ChatMessageToMapTransformer : DataTransformer<ChatMessage, Map<String, Any>> {

    companion object {
        const val SENT_KEY = "sent"
        const val FROM_KEY = "fromUserId"
        const val MESSAGE_KEY = "textMessage"
    }

    override fun transform(from: ChatMessage) =
        mapOf(
            SENT_KEY to from.sentDate,
            FROM_KEY to from.fromUserId,
            MESSAGE_KEY to from.text
        )
}