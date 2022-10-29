package com.dpconde.feature.chat.directory.di


import com.dpconde.feature.chat.directory.domain.data.local.MessageLocalRepository
import com.dpconde.feature.chat.directory.domain.data.local.MessageThreadLocalRepository
import com.dpconde.feature.chat.directory.domain.data.local.UserLocalRepository
import com.dpconde.feature.chat.directory.domain.data.remote.MessageThreadRemoteRepository
import com.dpconde.feature.chat.directory.domain.data.remote.UserRemoteRepository
import com.dpconde.feature.chat.directory.domain.entities.Message
import com.dpconde.feature.chat.directory.domain.entities.MessageThread
import com.dpconde.feature.chat.directory.domain.entities.User
import com.dpconde.feature.chat.directory.domain.service.DataSyncService
import com.dpconde.feature.chat.directory.domain.usecase.FetchMessageThreadsUseCaseImpl
import com.dpconde.feature.chat.directory.infrastructure.data.local.MessageRoomRepository
import com.dpconde.feature.chat.directory.infrastructure.data.local.MessageThreadRoomRepository
import com.dpconde.feature.chat.directory.infrastructure.data.local.UserRoomRepository
import com.dpconde.feature.chat.directory.infrastructure.data.remote.MessageThreadFirebaseRepository
import com.dpconde.feature.chat.directory.infrastructure.data.remote.UserFirebaseRepository
import com.dpconde.feature.chat.directory.infrastructure.datatransformer.message.DboMessageTransformer
import com.dpconde.feature.chat.directory.infrastructure.datatransformer.message.MessageDboTransformer
import com.dpconde.feature.chat.directory.infrastructure.datatransformer.message.MessageFirestoreTransformer
import com.dpconde.feature.chat.directory.infrastructure.datatransformer.thread.MessageDboThreadTransformer
import com.dpconde.feature.chat.directory.infrastructure.datatransformer.thread.MessageFirestoreThreadTransformer
import com.dpconde.feature.chat.directory.infrastructure.datatransformer.thread.MessageThreadDboTransformer
import com.dpconde.feature.chat.directory.infrastructure.datatransformer.user.FirebaseUserTransformer
import com.dpconde.feature.chat.directory.infrastructure.datatransformer.user.RoomUserTransformer
import com.dpconde.feature.chat.directory.infrastructure.datatransformer.user.UserRoomTransformer
import com.dpconde.feature.chat.directory.presentation.usecase.FetchMessageThreadsUseCase
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.dpconde.kaicare.core.persistence.room.dao.MessageDao
import com.dpconde.kaicare.core.persistence.room.dao.MessageThreadDao
import com.dpconde.kaicare.core.persistence.room.dao.UserDao
import com.dpconde.kaicare.core.persistence.room.dbo.MessageDbo
import com.dpconde.kaicare.core.persistence.room.dbo.MessageThreadDbo
import com.dpconde.kaicare.core.persistence.room.dbo.UserDbo
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides

@Module
class ChatDirectoryModule {

    @Provides
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun provideMessageThreadRemoteRepository(
        firestore: FirebaseFirestore,
        messageTransformer: DataTransformer<DocumentSnapshot, Message>,
        messageThreadTransformer: DataTransformer<DocumentSnapshot, MessageThread>
    ): MessageThreadRemoteRepository =
        MessageThreadFirebaseRepository(firestore, messageThreadTransformer, messageTransformer)

    @Provides
    fun provideMessageThreadLocalRepository(
        dao: MessageThreadDao,
        threadDboTransformer: DataTransformer<MessageThreadDbo, MessageThread>,
        threadTransformer: DataTransformer<MessageThread, MessageThreadDbo>
    ): MessageThreadLocalRepository =
        MessageThreadRoomRepository(dao, threadDboTransformer, threadTransformer)

    @Provides
    fun provideMessageLocalRepository(
        dao: MessageDao,
        messageDboTransformer: DataTransformer<MessageDbo, Message>,
        messageTransformer: DataTransformer<Message, MessageDbo>
    ): MessageLocalRepository =
        MessageRoomRepository(dao, messageDboTransformer, messageTransformer)

    @Provides
    fun provideDboMessageTransformer(): DataTransformer<MessageDbo, Message> =
        DboMessageTransformer()

    @Provides
    fun provideMessageDboTransformer(): DataTransformer<Message, MessageDbo> =
        MessageDboTransformer()

    @Provides
    fun provideMessageThreadTransformer(): DataTransformer<DocumentSnapshot, MessageThread> =
        MessageFirestoreThreadTransformer()

    @Provides
    fun provideMessageTransformer(): DataTransformer<DocumentSnapshot, Message> =
        MessageFirestoreTransformer()

    @Provides
    fun provideMessageDboThreadTransformer(): DataTransformer<MessageThreadDbo, MessageThread> =
        MessageDboThreadTransformer()

    @Provides
    fun provideMessageThreadDboTransformer(): DataTransformer<MessageThread, MessageThreadDbo> =
        MessageThreadDboTransformer()

    @Provides
    fun provideFetchMessageThreadUseCase(
        remoteRepository: MessageThreadRemoteRepository,
        localRepository: MessageThreadLocalRepository,
        userRemoteRepository: UserRemoteRepository,
        dataSyncService: DataSyncService,
    ): FetchMessageThreadsUseCase = FetchMessageThreadsUseCaseImpl(
        remoteRepository, userRemoteRepository, localRepository, dataSyncService)

    @Provides
    fun provideDataSyncService(
        localRepository: MessageThreadLocalRepository,
        localMessageRepository: MessageLocalRepository,
        userLocalRepository: UserLocalRepository,
    ): DataSyncService = DataSyncService(userLocalRepository,
        localMessageRepository, localRepository)

    @Provides
    fun provideUserRemoteRepository(
        firestore: FirebaseFirestore,
        firebaseUserTransformer: DataTransformer<DocumentSnapshot, User>
    ): UserRemoteRepository = UserFirebaseRepository(firestore, firebaseUserTransformer)

    @Provides
    fun provideUserLocalRepository(
        dao: UserDao,
        roomUserTransformer: DataTransformer<UserDbo, User>,
        userRoomTransformer: DataTransformer<User, UserDbo>,
    ): UserLocalRepository = UserRoomRepository(dao, roomUserTransformer, userRoomTransformer)

    @Provides
    fun provideFirebaseUserTransformer(): DataTransformer<DocumentSnapshot, User> = FirebaseUserTransformer()

    @Provides
    fun provideRoomUserTransformer(): DataTransformer<UserDbo, User> = RoomUserTransformer()

    @Provides
    fun provideUserRoomTransformer(): DataTransformer<User, UserDbo> = UserRoomTransformer()
}