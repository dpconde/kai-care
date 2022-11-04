package com.dpconde.kaicare.core.remotepersistence.di

import com.dpconde.kaicare.core.commons.domain.ChatMessage
import com.dpconde.kaicare.core.commons.domain.ChatThread
import com.dpconde.kaicare.core.commons.domain.User
import com.dpconde.kaicare.core.commons.service.DataTransformer
import com.dpconde.kaicare.core.remotepersistence.firestore.datatransformer.chatmessage.ChatMessageToMapTransformer
import com.dpconde.kaicare.core.remotepersistence.firestore.datatransformer.chatmessage.DocToChatMessageTransformer
import com.dpconde.kaicare.core.remotepersistence.firestore.datatransformer.chatthread.DocToChatThreadTransformer
import com.dpconde.kaicare.core.remotepersistence.firestore.datatransformer.user.DocToUserTransformer
import com.dpconde.kaicare.core.remotepersistence.firestore.repository.ChatMessageFirestoreRepository
import com.dpconde.kaicare.core.remotepersistence.firestore.repository.ChatThreadFirestoreRepository
import com.dpconde.kaicare.core.remotepersistence.firestore.repository.UserFirestoreRepository
import com.dpconde.kaicare.core.remotepersistence.repository.ChatMessageRemoteRepository
import com.dpconde.kaicare.core.remotepersistence.repository.ChatThreadRemoteRepository
import com.dpconde.kaicare.core.remotepersistence.repository.UserRemoteRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides

@Module
class RemotePersistenceModule {

    @Provides
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun provideChatThreadRemoteRepository(
        firestore: FirebaseFirestore,
        docToChatThreadTransformer: DataTransformer<DocumentSnapshot, ChatThread>
    ): ChatThreadRemoteRepository = ChatThreadFirestoreRepository(firestore, docToChatThreadTransformer)

    @Provides
    fun provideChatMessageRemoteRepository(
        firestore: FirebaseFirestore,
        docToChatMessageTransformer: DataTransformer<DocumentSnapshot, ChatMessage>,
        chatMessageToMapTransformer: DataTransformer<ChatMessage, Map<String, Any>>
    ): ChatMessageRemoteRepository = ChatMessageFirestoreRepository(firestore,
        docToChatMessageTransformer, chatMessageToMapTransformer)

    @Provides
    fun provideUserRemoteRepository(
        firestore: FirebaseFirestore,
        docToUserTransformer: DataTransformer<DocumentSnapshot, User>,
    ): UserRemoteRepository = UserFirestoreRepository(firestore, docToUserTransformer)

    @Provides
    fun provideDocToUserTransformer(): DataTransformer<DocumentSnapshot, User> =
        DocToUserTransformer()

    @Provides
    fun provideChatMessageToMapTransformer(): DataTransformer<ChatMessage, Map<String, Any>> =
        ChatMessageToMapTransformer()

    @Provides
    fun provideDocToChatMessageTransformer(): DataTransformer<DocumentSnapshot, ChatMessage> =
        DocToChatMessageTransformer()

    @Provides
    fun provideDocToChatThreadTransformer(): DataTransformer<DocumentSnapshot, ChatThread> =
        DocToChatThreadTransformer()

}