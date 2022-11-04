package com.dpconde.kaicare.core.localpersistence.di

import android.content.Context
import androidx.room.Room
import com.dpconde.kaicare.core.commons.domain.ChatMessage
import com.dpconde.kaicare.core.commons.domain.ChatThread
import com.dpconde.kaicare.core.commons.domain.User
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.dpconde.kaicare.core.localpersistence.repository.ChatMessageLocalRepository
import com.dpconde.kaicare.core.localpersistence.repository.ChatThreadLocalRepository
import com.dpconde.kaicare.core.localpersistence.repository.UserLocalRepository
import com.dpconde.kaicare.core.localpersistence.room.AppDataBase
import com.dpconde.kaicare.core.localpersistence.room.dao.MessageDao
import com.dpconde.kaicare.core.localpersistence.room.dao.MessageThreadDao
import com.dpconde.kaicare.core.localpersistence.room.dao.UserDao
import com.dpconde.kaicare.core.localpersistence.room.datatransformer.chatmessage.DboToMessageDataTransformer
import com.dpconde.kaicare.core.localpersistence.room.datatransformer.chatmessage.MessageToDboDataTransformer
import com.dpconde.kaicare.core.localpersistence.room.datatransformer.chatthread.DboToThreadDataTransformer
import com.dpconde.kaicare.core.localpersistence.room.datatransformer.chatthread.ThreadToDboDataTransformer
import com.dpconde.kaicare.core.localpersistence.room.datatransformer.user.DboToUserDataTransformer
import com.dpconde.kaicare.core.localpersistence.room.datatransformer.user.UserToDboDataTransformer
import com.dpconde.kaicare.core.localpersistence.room.dbo.MessageDbo
import com.dpconde.kaicare.core.localpersistence.room.dbo.MessageThreadDbo
import com.dpconde.kaicare.core.localpersistence.room.dbo.UserDbo
import com.dpconde.kaicare.core.localpersistence.room.repository.ChatMessageRoomRepository
import com.dpconde.kaicare.core.localpersistence.room.repository.ChatThreadRoomRepository
import com.dpconde.kaicare.core.localpersistence.room.repository.UserRoomRepository
import dagger.Module
import dagger.Provides

@Module
class LocalPersistenceModule {

    @Provides
    fun provideRoomDataBase(context: Context): AppDataBase =
        Room.databaseBuilder(context, AppDataBase::class.java, "kai-care-db").build()

    @Provides
    fun provideMessageThreadDao(db: AppDataBase): MessageThreadDao = db.messageThreadDao()

    @Provides
    fun provideMessageDao(db: AppDataBase): MessageDao = db.messageDao()

    @Provides
    fun provideUserDao(db: AppDataBase): UserDao = db.userDao()

    @Provides
    fun provideUserRepository(
        userDao: UserDao,
        dboToUserTransformer: DataTransformer<UserDbo, User>,
        userToDboTransformer: DataTransformer<User, UserDbo>
    ): UserLocalRepository = UserRoomRepository(userDao, dboToUserTransformer, userToDboTransformer)

    @Provides
    fun provideChatMessageRepository(
        messageDao: MessageDao,
        dboToChatMessageTransformer: DataTransformer<MessageDbo, ChatMessage>,
        chatMessageToDboTransformer: DataTransformer<ChatMessage, MessageDbo>
    ): ChatMessageLocalRepository = ChatMessageRoomRepository(messageDao, dboToChatMessageTransformer,
        chatMessageToDboTransformer)

    @Provides
    fun provideChatThreadRepository(
        threadDao: MessageThreadDao,
        dboToThreadTransformer: DataTransformer<MessageThreadDbo, ChatThread>,
        threadToDboTransformer: DataTransformer<ChatThread, MessageThreadDbo>
    ): ChatThreadLocalRepository = ChatThreadRoomRepository(threadDao, dboToThreadTransformer,
        threadToDboTransformer)

    @Provides
    fun provideThreadToDboTransformer(): DataTransformer<ChatThread, MessageThreadDbo> =
        ThreadToDboDataTransformer()

    @Provides
    fun provideDboToThreadTransformer(): DataTransformer<MessageThreadDbo, ChatThread> =
        DboToThreadDataTransformer()

    @Provides
    fun provideDboToChatMessageTransformer(): DataTransformer<MessageDbo, ChatMessage> =
        DboToMessageDataTransformer()

    @Provides
    fun provideChatMessageToDboTransformer(): DataTransformer<ChatMessage, MessageDbo> =
        MessageToDboDataTransformer()

    @Provides
    fun provideDboUserTransformer(): DataTransformer<UserDbo, User> = DboToUserDataTransformer()

    @Provides
    fun provideUserDboTransformer(): DataTransformer<User, UserDbo> = UserToDboDataTransformer()

}