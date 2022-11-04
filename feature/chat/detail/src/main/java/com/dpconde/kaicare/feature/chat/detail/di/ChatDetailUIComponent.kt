package com.dpconde.kaicare.feature.chat.detail.di

import android.content.Context
import com.dpconde.kaicare.AppDependencies
import com.dpconde.kaicare.core.localpersistence.di.LocalPersistenceModule
import com.dpconde.kaicare.core.remotepersistence.di.RemotePersistenceModule
import com.dpconde.kaicare.core.session.di.SessionModule
import com.dpconde.kaicare.feature.chat.detail.presentation.ChatDetailFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [AppDependencies::class],
    modules = [
        ChatDetailModule::class,
        ChatDetailUIModule::class,
        LocalPersistenceModule::class,
        RemotePersistenceModule::class,
        SessionModule::class
    ]
)
interface ChatDetailUIComponent {
    fun inject (fragment: ChatDetailFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(daggerDependencies: AppDependencies): Builder
        fun build(): ChatDetailUIComponent
    }
}