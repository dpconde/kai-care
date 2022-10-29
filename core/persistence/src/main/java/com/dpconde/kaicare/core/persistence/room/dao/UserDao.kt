package com.dpconde.kaicare.core.persistence.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dpconde.kaicare.core.persistence.room.dbo.UserDbo

@Dao
interface UserDao {

    @Query("SELECT * FROM UserDbo")
    fun getAll(): List<UserDbo>

    @Query("SELECT * FROM UserDbo WHERE id = :userId")
    fun getById(userId: String): UserDbo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(user: List<UserDbo>)

}

