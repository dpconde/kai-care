package com.dpconde.kaicare.feature.chat.detail.domain.usecase

import com.dpconde.kaicare.core.localpersistence.repository.ChatThreadLocalRepository
import com.dpconde.kaicare.feature.chat.detail.presentation.usecase.UpdateLastFetchDateUseCase
import java.util.*
import javax.inject.Inject

class UpdateLastFetchDateUseCaseImpl @Inject constructor(
    private val chatThreadLocalRepository: ChatThreadLocalRepository
): UpdateLastFetchDateUseCase {

    override suspend fun updateLastFetch(threadId: String, date: Date) {
        chatThreadLocalRepository.updateLastFetchData(threadId, date)
    }
}