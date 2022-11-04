package com.dpconde.kaicare.feature.chat.detail.presentation.usecase

import java.util.*

interface UpdateLastFetchDateUseCase {

    suspend fun updateLastFetch(threadId: String, date: Date)

}