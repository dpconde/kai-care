package com.dpconde.feature.chat.directory.di


import com.dpconde.feature.chat.directory.domain.service.DataSyncService
import com.dpconde.feature.chat.directory.domain.usecase.FetchMessageThreadsUseCaseImpl
import com.dpconde.feature.chat.directory.presentation.usecase.FetchMessageThreadsUseCase
import com.dpconde.kaicare.core.localpersistence.repository.ChatMessageLocalRepository
import com.dpconde.kaicare.core.localpersistence.repository.ChatThreadLocalRepository
import com.dpconde.kaicare.core.localpersistence.repository.UserLocalRepository
import com.dpconde.kaicare.core.remotepersistence.repository.ChatMessageRemoteRepository
import com.dpconde.kaicare.core.remotepersistence.repository.ChatThreadRemoteRepository
import com.dpconde.kaicare.core.remotepersistence.repository.UserRemoteRepository
import com.dpconde.kaicare.core.session.service.SessionManager
import dagger.Module
import dagger.Provides

@Module
class ChatDirectoryModule {

    @Provides
    fun provideFetchMessageThreadUseCase(
        chatMessageRemoteRepository: ChatMessageRemoteRepository,
        remoteRepository: ChatThreadRemoteRepository,
        localRepository: ChatThreadLocalRepository,
        userRemoteRepository: UserRemoteRepository,
        dataSyncService: DataSyncService,
        sessionManager: SessionManager
    ): FetchMessageThreadsUseCase = FetchMessageThreadsUseCaseImpl(
        chatMessageRemoteRepository, userRemoteRepository, remoteRepository, localRepository,
        dataSyncService, sessionManager)

    @Provides
    fun provideDataSyncService(
        localRepository: ChatThreadLocalRepository,
        localMessageRepository: ChatMessageLocalRepository,
        userLocalRepository: UserLocalRepository,
        sessionManager: SessionManager
    ): DataSyncService = DataSyncService(userLocalRepository,
        localMessageRepository, localRepository, sessionManager)

}