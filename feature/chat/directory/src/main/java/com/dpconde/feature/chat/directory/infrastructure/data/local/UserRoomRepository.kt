package com.dpconde.feature.chat.directory.infrastructure.data.local

import com.dpconde.feature.chat.directory.domain.data.local.UserLocalRepository
import com.dpconde.feature.chat.directory.domain.entities.User
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.dpconde.kaicare.core.persistence.room.dao.UserDao
import com.dpconde.kaicare.core.persistence.room.dbo.UserDbo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRoomRepository @Inject constructor(
    private val dao: UserDao,
    private val roomUserTransformer: DataTransformer<UserDbo, User>,
    private val userRoomTransformer: DataTransformer<User, UserDbo>,
    ): UserLocalRepository {

    override suspend fun fetchAll(): List<User> =
        withContext(Dispatchers.IO){
            dao.getAll().map { roomUserTransformer.transform(it) }
        }

    override suspend fun fetchById(userId: String) =
        withContext(Dispatchers.IO){
            roomUserTransformer.transform(
                dao.getById(userId)
            )
        }

    override suspend fun save(users: List<User>) =
        withContext(Dispatchers.IO){
            val transformed = users.map { userRoomTransformer.transform(it) }
            dao.insertAll(transformed)
        }
}