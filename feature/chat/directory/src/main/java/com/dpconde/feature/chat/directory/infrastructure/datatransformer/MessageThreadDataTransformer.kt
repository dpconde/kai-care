package com.dpconde.feature.chat.directory.infrastructure.datatransformer

import com.dpconde.feature.chat.directory.domain.entities.MessageThread
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.google.firebase.firestore.DocumentSnapshot

class MessageThreadDataTransformer: DataTransformer<DocumentSnapshot, MessageThread> {

    companion object{
        const val TYPE_KEY = "type"

    }

    override fun transform(from: DocumentSnapshot): MessageThread {
        TODO("Not yet implemented")
    }
}