package com.dpconde.kaicare.core.remotepersistence.firestore.datatransformer.chatthread

import com.dpconde.kaicare.core.commons.domain.ChatThread
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.google.firebase.firestore.DocumentSnapshot

class DocToChatThreadTransformer : DataTransformer<DocumentSnapshot, ChatThread> {

    companion object{
        const val MEMBERS_KEY = "members"
    }

    @Suppress("UNCHECKED_CAST")
    override fun transform(from: DocumentSnapshot) =
        ChatThread(
            id = from.id,
            lastFetch = null,
            members = from.get(MEMBERS_KEY) as List<String>,
            lastMessage = null,
            lastMessageDate = null,
            unprocessedMessages = null,
            unreadMessages = null,
            name = "",
            role = "",
            imageUrl = ""
        )
}