package com.dpconde.feature.chat.directory.infrastructure.datatransformer.message

import com.dpconde.feature.chat.directory.domain.entities.Message
import com.dpconde.feature.chat.directory.domain.entities.MessageType
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.google.firebase.firestore.DocumentSnapshot

class MessageFirestoreTransformer: DataTransformer<DocumentSnapshot, Message> {

    companion object{
        const val TEXT_MESSAGE_KEY = "textMessage"
        const val SENT_KEY = "sent"
    }

    override fun transform(from: DocumentSnapshot): Message {
        return Message(
            from.id,
            from.reference.parent.parent!!.id,
            MessageType.TEXT,
            from.getString(TEXT_MESSAGE_KEY) ?: "",
            from.getTimestamp(SENT_KEY)?.toDate()!!
        )
    }
}