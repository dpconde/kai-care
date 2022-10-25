package com.dpconde.feature.chat.directory.domain.usecase

import com.dpconde.feature.chat.directory.domain.data.MessageThreadRepository
import com.dpconde.feature.chat.directory.domain.entities.MessageThread
import com.dpconde.feature.chat.directory.presentation.usecase.FetchMessageThreadsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.util.*
import javax.inject.Inject

class FetchMessageThreadsUseCaseImpl @Inject constructor(
    private val repository: MessageThreadRepository
    //TODO Algo para obtener el id de usuario loggeado
    //TODO Algo para obtener los mensajes en local
): FetchMessageThreadsUseCase {

    override suspend fun fetchMessageThreads(): List<MessageThread>{
        //Local threads and messages
        //TODO
        val localData = listOf<MessageThread>()

        //Remote threads and messages
        val remoteData = repository.getMessageThreadsByUser("")
        coroutineScope {
            remoteData.map { messageThread ->
                async(Dispatchers.IO){
                    val messages = repository.getMessagesByThreadIdAndDate(messageThread.id, Date())
                    messageThread.unprocessedMessages = messages
                }
            }.awaitAll()
        }

        return remoteData
    }

    private fun syncLocalData(remoteData: List<MessageThread>, localData: List<MessageThread>){

    }

}