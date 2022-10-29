package com.dpconde.feature.chat.directory.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpconde.feature.chat.directory.domain.entities.MessageThread
import com.dpconde.feature.chat.directory.presentation.usecase.FetchMessageThreadsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class ChatDirectoryViewModel @Inject constructor(
    private val fetchMessageThreadsUseCase: FetchMessageThreadsUseCase
) : ViewModel() {

    val messageThreads = MutableLiveData<List<MessageThread>>()

    fun fetchMessageThreads() {
        viewModelScope.launch(Dispatchers.Main) {

            val threads = fetchMessageThreadsUseCase.fetchMessageThreads()
            messageThreads.postValue(threads)
        }
    }

    fun fetchLocalMessageThreads() {
        viewModelScope.launch(Dispatchers.Main) {
            val threads = fetchMessageThreadsUseCase.fetchCurrentMessageThreads()
            messageThreads.postValue(threads)
        }
    }
}