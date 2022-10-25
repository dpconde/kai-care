package com.dpconde.feature.chat.directory.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpconde.feature.chat.directory.presentation.usecase.FetchMessageThreadsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class ChatDirectoryViewModel @Inject constructor(
    private val fetchMessageThreadsUseCase: FetchMessageThreadsUseCase
) : ViewModel() {

    fun fetchMessageThreads() {
        viewModelScope.launch(Dispatchers.Main) {
            val a = fetchMessageThreadsUseCase.fetchMessageThreads()
        }
    }

}