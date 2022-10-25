package com.dpconde.feature.chat.directory.domain.entities

import java.util.*

data class Message(
    val id: String,
    val createdAt: Date = Date(),
    val type: MessageType,
    val textMessage: String,
    val deliveredAt: Date?,
    val seenAt: Date?
)
