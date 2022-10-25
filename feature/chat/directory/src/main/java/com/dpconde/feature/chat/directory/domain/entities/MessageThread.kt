package com.dpconde.feature.chat.directory.domain.entities

import java.util.*

data class MessageThread(
    val id: String,
    val isGroup: Boolean, //group, personal
    var lastFetch: Date? = null,
    val members: List<String> = listOf(),
    var lastMessage: String? = null,
    var unprocessedMessages: List<Message>? = null,
    var unreadMessages: Int? = null
)
