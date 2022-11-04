package com.dpconde.kaicare.core.commons.domain

import java.util.*

data class ChatThread(
    val id: String,
    var lastFetch: Date?,
    val members: List<String> = listOf(),
    var lastMessage: String?,
    var lastMessageDate: Date?,
    var unprocessedMessages: List<ChatMessage>?,
    var unreadMessages: Int? = null,
    var name: String,
    var role: String,
    var imageUrl: String
)