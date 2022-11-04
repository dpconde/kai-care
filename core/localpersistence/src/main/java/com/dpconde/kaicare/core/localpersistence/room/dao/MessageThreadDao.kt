package com.dpconde.kaicare.core.localpersistence.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dpconde.kaicare.core.localpersistence.room.dbo.MessageThreadDbo
import java.util.*

@Dao
interface MessageThreadDao {

    @Query("SELECT * FROM MessageThreadDbo")
    fun getAll(): List<MessageThreadDbo>

    @Query("SELECT * FROM MessageThreadDbo WHERE id = :threadId")
    fun getById(threadId: String): MessageThreadDbo

    @Query("UPDATE MessageThreadDbo SET lastFetch = :lastFetch WHERE id = :threadId")
    fun updateLastFetchDate(threadId: String, lastFetch: Date = Date())

    @Query("UPDATE MessageThreadDbo SET unreadMessages = 0 WHERE id = :threadId")
    fun resetUnreadMessagesByThreadId(threadId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(messageThreads: List<MessageThreadDbo>)

}

