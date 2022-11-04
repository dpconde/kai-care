package com.dpconde.kaicare.feature.chat.detail.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpconde.kaicare.core.commons.domain.ChatMessage
import com.dpconde.kaicare.feature.chat.detail.presentation.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


class ChatDetailViewModel @Inject constructor(
    private val fetchRealTimeMessagesUseCase: FetchRealTimeMessagesUseCase,
    private val fetchStoredMessagesUseCase: FetchStoredMessagesUseCase,
    private val saveMessagesLocalUseCase: SaveMessagesLocalUseCase,
    private val updateLastFetchDateUseCase: UpdateLastFetchDateUseCase,
    private val setAllLocalMessagesAsReadUseCase: SetAllLocalMessagesAsReadUseCase,
    private val getUserSessionIdUseCase: GetUserSessionIdUseCase,
    private val sendMessageUseCase: SendMessageUseCase
) : ViewModel() {

    val messages = MutableLiveData<List<ChatMessage>>()
    val message = MutableLiveData<String>()

    fun startFetchingRealTimeMessages(threadId: String) {
        viewModelScope.launch(Dispatchers.Main) {
            fetchRealTimeMessagesUseCase.startListeningForMessages(threadId)
                .onEach {
                    saveMessagesLocalUseCase.saveMessagesLocal(it)
                    updateLastFetchDateUseCase.updateLastFetch(
                        threadId, it.firstOrNull()?.sentDate ?: Date())
                }
                .cancellable()
                .collect { items ->
                    //TODO haz algo con esto
                    val aux = messages.value?.toMutableList() ?: mutableListOf()
                    aux.addAll(items)
                    aux.let {
                    messages.postValue(aux)
                }
            }
        }
    }

    fun sendMessage(threadId: String){
        viewModelScope.launch(Dispatchers.Main) {
            message.value?.let {
                sendMessageUseCase.send(it, threadId)
            }
        }
    }

    fun fetchMessages(threadId: String){
        messages.value = listOf()
        viewModelScope.launch(Dispatchers.Main) {
            val messagesResponse = fetchStoredMessagesUseCase.fetchAllMessagesByThreadId(threadId)
            messages.postValue(messagesResponse)
        }
    }

    fun onChatOpened(threadId: String){
        viewModelScope.launch(Dispatchers.Main) {
            setAllLocalMessagesAsReadUseCase.set(threadId)
        }
    }

    fun getSessionUserId() = getUserSessionIdUseCase.get()

}