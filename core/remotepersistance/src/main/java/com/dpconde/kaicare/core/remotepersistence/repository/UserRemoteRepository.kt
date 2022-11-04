package com.dpconde.kaicare.core.remotepersistence.repository

import com.dpconde.kaicare.core.commons.domain.User

interface UserRemoteRepository {

    suspend fun getAllUsers(): List<User>
}