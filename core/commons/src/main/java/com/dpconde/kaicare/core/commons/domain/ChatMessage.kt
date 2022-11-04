package com.dpconde.kaicare.core.commons.domain

import java.util.*

data class ChatMessage(
    val id: String,
    val text: String,
    val fromUserId: String,
    val sentDate: Date,
    val threadId: String
){
    fun isMine(userId: String) = userId==fromUserId
}
