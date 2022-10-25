package com.dpconde.kaicare.core.persistence.di

import android.content.Context
import androidx.room.Room
import com.dpconde.kaicare.core.persistence.room.AppDataBase
import com.dpconde.kaicare.core.persistence.room.dao.MessageDao
import com.dpconde.kaicare.core.persistence.room.dao.MessageThreadDao
import dagger.Module
import dagger.Provides

@Module
class PersistenceModule {

    @Provides
    fun provideRoomDataBase(context: Context): AppDataBase =
        Room.databaseBuilder(context, AppDataBase::class.java, "kai-care-db").build()

    @Provides
    fun provideMessageThreadDao(db: AppDataBase): MessageThreadDao = db.messageThreadDao()

    @Provides
    fun provideMessageDao(db: AppDataBase): MessageDao = db.messageDao()

}