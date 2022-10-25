package com.dpconde.feature.chat.directory.domain.entities

import java.util.*

data class MessageThread(
    val id: String,
    val type: Int, //group, personal
    val lastFetch: Date? = null,
    var unprocessedMessages: List<Message> = listOf(),
    var processedMessages: List<Message> = listOf(),
    var unreadMessages: Int = 19
)
