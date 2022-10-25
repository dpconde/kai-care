package com.dpconde.kaicare.core.persistence.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dpconde.kaicare.core.persistence.room.dbo.MessageThreadDbo

@Dao
interface MessageThreadDao {

    @Query("SELECT * FROM MessageThreadDbo")
    fun getAll(): List<MessageThreadDbo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(messageThreads: List<MessageThreadDbo>)

}

