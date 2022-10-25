package com.dpconde.feature.chat.directory.di


import com.dpconde.feature.chat.directory.domain.data.MessageThreadRepository
import com.dpconde.feature.chat.directory.domain.usecase.FetchMessageThreadsUseCaseImpl
import com.dpconde.feature.chat.directory.infrastructure.data.MessageThreadFirebaseRepository
import com.dpconde.feature.chat.directory.presentation.usecase.FetchMessageThreadsUseCase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MessageThreadCollection

@Module
class ChatDirectoryModule {

    @MessageThreadCollection
    @Provides
    fun provideGroupCollectionRef(
        firestore: FirebaseFirestore
    ): CollectionReference = firestore.collection("MESSAGE_THREADS")

    @Provides
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun provideMessageThreadFirebaseRepository(
        @MessageThreadCollection collection: CollectionReference
    ): MessageThreadRepository = MessageThreadFirebaseRepository(collection)

    @Provides
    fun provideFetchMessageThreadUseCase(
        repository: MessageThreadRepository
    ): FetchMessageThreadsUseCase = FetchMessageThreadsUseCaseImpl(repository)
}