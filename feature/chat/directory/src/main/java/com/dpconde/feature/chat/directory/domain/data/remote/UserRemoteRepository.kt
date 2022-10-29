package com.dpconde.feature.chat.directory.domain.data.remote

import com.dpconde.feature.chat.directory.domain.entities.User

interface UserRemoteRepository{

    /**
     * Get all users
     */
    suspend fun getAllUsers(): List<User>

}