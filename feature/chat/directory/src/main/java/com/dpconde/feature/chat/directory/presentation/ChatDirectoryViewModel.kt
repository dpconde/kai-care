package com.dpconde.feature.chat.directory.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpconde.feature.chat.directory.presentation.usecase.FetchMessageThreadsUseCase
import com.dpconde.kaicare.core.commons.domain.ChatThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class ChatDirectoryViewModel @Inject constructor(
    private val fetchMessageThreadsUseCase: FetchMessageThreadsUseCase
) : ViewModel() {

    val chatThreads = MutableLiveData<List<ChatThread>>()

    fun fetchMessageThreads() {
        viewModelScope.launch(Dispatchers.Main) {

            val threads = fetchMessageThreadsUseCase.fetchMessageThreads()
            chatThreads.postValue(threads)
        }
    }

    fun fetchLocalMessageThreads() {
        viewModelScope.launch(Dispatchers.Main) {
            val threads = fetchMessageThreadsUseCase.fetchCurrentMessageThreads()
            chatThreads.postValue(threads)
        }
    }
}