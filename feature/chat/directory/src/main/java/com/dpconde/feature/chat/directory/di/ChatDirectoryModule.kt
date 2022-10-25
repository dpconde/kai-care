package com.dpconde.feature.chat.directory.di


import com.dpconde.feature.chat.directory.domain.data.MessageLocalRepository
import com.dpconde.feature.chat.directory.domain.data.MessageThreadLocalRepository
import com.dpconde.feature.chat.directory.domain.data.MessageThreadRemoteRepository
import com.dpconde.feature.chat.directory.domain.entities.Message
import com.dpconde.feature.chat.directory.domain.entities.MessageThread
import com.dpconde.feature.chat.directory.domain.usecase.FetchMessageThreadsUseCaseImpl
import com.dpconde.feature.chat.directory.infrastructure.data.MessageRoomRepository
import com.dpconde.feature.chat.directory.infrastructure.data.MessageThreadFirebaseRepository
import com.dpconde.feature.chat.directory.infrastructure.data.MessageThreadRoomRepository
import com.dpconde.feature.chat.directory.infrastructure.datatransformer.*
import com.dpconde.feature.chat.directory.presentation.usecase.FetchMessageThreadsUseCase
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.dpconde.kaicare.core.persistence.room.dao.MessageDao
import com.dpconde.kaicare.core.persistence.room.dao.MessageThreadDao
import com.dpconde.kaicare.core.persistence.room.dbo.MessageDbo
import com.dpconde.kaicare.core.persistence.room.dbo.MessageThreadDbo
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
        localMessageRepository: MessageLocalRepository,
    ): FetchMessageThreadsUseCase = FetchMessageThreadsUseCaseImpl(
        remoteRepository, localRepository, localMessageRepository
    )
}