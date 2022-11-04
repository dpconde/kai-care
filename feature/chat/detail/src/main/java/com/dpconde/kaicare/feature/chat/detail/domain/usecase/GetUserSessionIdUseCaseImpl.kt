package com.dpconde.kaicare.feature.chat.detail.domain.usecase

import com.dpconde.kaicare.core.session.service.SessionManager
import com.dpconde.kaicare.feature.chat.detail.presentation.usecase.GetUserSessionIdUseCase
import javax.inject.Inject

class GetUserSessionIdUseCaseImpl@Inject constructor(
    private val sessionManager: SessionManager
) : GetUserSessionIdUseCase {

    override fun get() = sessionManager.getSessionUserId() ?: "" //TODO throw exception instead of ""
}