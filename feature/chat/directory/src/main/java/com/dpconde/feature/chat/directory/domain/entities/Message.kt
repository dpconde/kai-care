package com.dpconde.feature.chat.directory.domain.entities

import java.util.*

data class Message(
    val id: String,
    val threadId: String,
    val type: MessageType,
    val textMessage: String,
    val sent: Date,
    val seen: Date? = null
)
