package com.dpconde.feature.chat.directory.infrastructure.datatransformer

import com.dpconde.feature.chat.directory.domain.entities.MessageThread
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.google.firebase.firestore.DocumentSnapshot

class MessageFirestoreThreadTransformer: DataTransformer<DocumentSnapshot, MessageThread> {

    companion object{
        const val TYPE_IS_GROUP = "isGroup"
        const val MEMBERS_KEY = "members"
    }

    override fun transform(from: DocumentSnapshot): MessageThread {
        return MessageThread(
            from.id,
            from.getBoolean(TYPE_IS_GROUP)?:false,
            null,
            getMembers(from),
            null,
            listOf(),
            null)
    }

    @Suppress("UNCHECKED_CAST")
    private fun getMembers(from: DocumentSnapshot) =
        from.get(MEMBERS_KEY) as List<String>
}