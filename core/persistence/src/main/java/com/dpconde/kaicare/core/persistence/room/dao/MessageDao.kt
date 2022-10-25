package com.dpconde.kaicare.core.persistence.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dpconde.kaicare.core.persistence.room.dbo.MessageDbo

@Dao
interface MessageDao {

    @Query("SELECT * FROM MessageDbo WHERE threadId = :threadId ORDER BY sent desc")
    fun getByThreadId(threadId: String): List<MessageDbo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(messageThread: List<MessageDbo>)

}

