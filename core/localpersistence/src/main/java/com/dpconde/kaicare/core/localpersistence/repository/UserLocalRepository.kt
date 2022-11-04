package com.dpconde.kaicare.core.localpersistence.repository

import com.dpconde.kaicare.core.commons.domain.User

interface UserLocalRepository {

    suspend fun fetchAll(): List<User>

    suspend fun fetchById(userId: String): User

    suspend fun save(users: List<User>)

}