package com.dpconde.kaicare.core.persistence.room.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDbo (
    @PrimaryKey
    val id: String,
    val name: String,
    val role: String,
    val profileImg: String
)