package com.dpconde.kaicare.core.localpersistence.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dpconde.kaicare.core.localpersistence.room.dbo.MessageDbo

@Dao
interface MessageDao {

    @Query("SELECT * FROM MessageDbo WHERE threadId = :threadId ORDER BY sent asc")
    fun getByThreadId(threadId: String): List<MessageDbo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(messageThread: List<MessageDbo>)

    @Query("UPDATE MessageDbo SET seen = Date() WHERE id = :threadId AND seen IS NULL")
    fun setAllAsReadByThreadId(threadId: String)

}

