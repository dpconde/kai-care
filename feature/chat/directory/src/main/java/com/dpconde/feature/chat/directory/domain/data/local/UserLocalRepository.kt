package com.dpconde.feature.chat.directory.domain.data.local

import com.dpconde.feature.chat.directory.domain.entities.User

interface UserLocalRepository{

    suspend fun fetchAll(): List<User>

    suspend fun fetchById(userId: String): User

    suspend fun save(users: List<User>)

}