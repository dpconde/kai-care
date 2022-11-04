package com.dpconde.kaicare.core.localpersistence.room.repository

import com.dpconde.kaicare.core.commons.domain.User
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.dpconde.kaicare.core.localpersistence.repository.UserLocalRepository
import com.dpconde.kaicare.core.localpersistence.room.dao.UserDao
import com.dpconde.kaicare.core.localpersistence.room.dbo.UserDbo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRoomRepository @Inject constructor(
    private val dao: UserDao,
    private val dboToUserTransformer: DataTransformer<UserDbo, User>,
    private val userToDboTransformer: DataTransformer<User, UserDbo>,
): UserLocalRepository {

    override suspend fun fetchAll() = withContext(Dispatchers.IO){
        dao.getAll().map { dboToUserTransformer.transform(it) }
    }

    override suspend fun fetchById(userId: String) = withContext(Dispatchers.IO){
        dboToUserTransformer.transform(dao.getById(userId))
    }

    override suspend fun save(users: List<User>) = withContext(Dispatchers.IO) {
        dao.insertAll(users.map { userToDboTransformer.transform(it) })
    }
}