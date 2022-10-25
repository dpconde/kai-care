package com.dpconde.kaicare.core.persistence.room.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class MessageDbo(
    @PrimaryKey
    val id: String,
    val threadId: String,
    val textMessage: String,
    val sent: Date,
    val seen: Date?
)