package com.dpconde.feature.chat.directory.domain.entities

data class User(
    val id: String,
    val name: String,
    val role: String,
    val imageUrl: String
)
