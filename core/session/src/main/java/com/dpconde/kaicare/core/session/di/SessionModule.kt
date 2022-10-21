package com.dpconde.kaicare.core.session.di

import android.content.Context
import com.dpconde.kaicare.core.session.service.SessionManager
import com.dpconde.kaicare.core.session.service.SessionManagerImpl
import dagger.Module
import dagger.Provides

@Module
class SessionModule {

    @Provides
    fun sessionManagerProvider(context: Context): SessionManager = SessionManagerImpl(context)
}