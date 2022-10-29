package com.dpconde.kaicare.core.persistence.room.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class MessageThreadDbo(
    @PrimaryKey
    val id: String,
    val lastFetch: Date?,
    val unreadMessages: Int,
    val lastMessage: String?,
    val lastMessageDate: Date?,
    val isGroup: Boolean,
    val name: String,
    val userProfile: String?,
    val imageUrl: String
)
