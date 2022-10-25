package com.dpconde.kaicare.core.persistence.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dpconde.kaicare.core.persistence.room.converter.Converters
import com.dpconde.kaicare.core.persistence.room.dao.MessageDao
import com.dpconde.kaicare.core.persistence.room.dao.MessageThreadDao
import com.dpconde.kaicare.core.persistence.room.dbo.MessageDbo
import com.dpconde.kaicare.core.persistence.room.dbo.MessageThreadDbo

@Database(entities = [MessageThreadDbo::class, MessageDbo::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDataBase: RoomDatabase() {
    abstract fun messageThreadDao(): MessageThreadDao
    abstract fun messageDao(): MessageDao
}