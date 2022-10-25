package com.dpconde.feature.chat.directory.presentation

import android.util.Log
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

            Log.e("DPC", "fullStart")
            val a = fetchMessageThreadsUseCase.fetchMessageThreads()
            Log.e("DPC", "fullEnd")


//            Log.e("DPC", "localstart")
//            val b = fetchMessageThreadsUseCase.fetchCurrentMessageThreads()
//            Log.e("DPC", "localend")

            val f = 0
        }
    }

}