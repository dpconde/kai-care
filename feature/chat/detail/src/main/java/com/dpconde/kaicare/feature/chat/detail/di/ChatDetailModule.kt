package com.dpconde.kaicare.feature.chat.detail.di


import com.dpconde.kaicare.core.localpersistence.repository.ChatMessageLocalRepository
import com.dpconde.kaicare.core.localpersistence.repository.ChatThreadLocalRepository
import com.dpconde.kaicare.core.remotepersistence.repository.ChatMessageRemoteRepository
import com.dpconde.kaicare.core.session.service.SessionManager
import com.dpconde.kaicare.feature.chat.detail.domain.usecase.*
import com.dpconde.kaicare.feature.chat.detail.presentation.usecase.*
import dagger.Module
import dagger.Provides

@Module
class ChatDetailModule {

    @Provides
    fun provideFetchRealTimeMessagesUseCase(
        chatMessageRemoteRepository: ChatMessageRemoteRepository,
        chatThreadLocalRepository: ChatThreadLocalRepository
    ): FetchRealTimeMessagesUseCase = FetchRealTimeMessagesUseCaseImpl(
        chatMessageRemoteRepository, chatThreadLocalRepository)

    @Provides
    fun provideFetchStoredMessagesUseCase(
        messageLocalRepository: ChatMessageLocalRepository
    ): FetchStoredMessagesUseCase = FetchStoredMessagesUseCaseImpl(messageLocalRepository)

    @Provides
    fun provideSendMessageUseCase(
        chatMessageLocalRepository: ChatMessageLocalRepository,
        chatMessageRemoteRepository: ChatMessageRemoteRepository,
        sessionManager: SessionManager
    ): SendMessageUseCase = SendMessageUseCaseImpl(
        chatMessageLocalRepository, chatMessageRemoteRepository, sessionManager)

    @Provides
    fun provideSaveMessagesLocalUseCase(
        messageLocalRepository: ChatMessageLocalRepository
    ): SaveMessagesLocalUseCase = SaveMessagesLocalUseCaseImpl(messageLocalRepository)

    @Provides
    fun provideUpdateLastFetchDateUseCase(
        localThreadRepository: ChatThreadLocalRepository
    ): UpdateLastFetchDateUseCase = UpdateLastFetchDateUseCaseImpl(localThreadRepository)

    @Provides
    fun provideSetAllLocalMessagesAsReadUseCase(
        messageLocalRepository: ChatMessageLocalRepository,
        localThreadRepository: ChatThreadLocalRepository): SetAllLocalMessagesAsReadUseCase
    = SetAllLocalMessagesAsReadUseCaseImpl(messageLocalRepository, localThreadRepository)

    @Provides
    fun provideGetUserSessionIdUseCase(
        sessionManager: SessionManager
    ): GetUserSessionIdUseCase = GetUserSessionIdUseCaseImpl(sessionManager)

}