package com.dpconde.kaicare.core.remotepersistence.firestore.datatransformer.chatmessage

import com.dpconde.kaicare.core.commons.domain.ChatMessage
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.google.firebase.firestore.DocumentSnapshot

class DocToChatMessageTransformer : DataTransformer<DocumentSnapshot, ChatMessage> {

    companion object{
        const val FROM_USER_ID_KEY = "fromUserId"
        const val TEXT_MESSAGE_KEY = "textMessage"
        const val SENT_KEY = "sent"
    }

    override fun transform(from: DocumentSnapshot) =
        ChatMessage(
            id = from.id,
            text = from.getString(TEXT_MESSAGE_KEY) ?: "",
            fromUserId = from.getString(FROM_USER_ID_KEY)!!,
            sentDate = from.getDate(SENT_KEY)!!,
            threadId = from.reference.parent.parent!!.id,
        )
}