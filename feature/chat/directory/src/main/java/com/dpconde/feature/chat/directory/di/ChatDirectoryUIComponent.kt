package com.dpconde.feature.chat.directory.di

import android.content.Context
import com.dpconde.feature.chat.directory.presentation.ChatDirectoryFragment
import com.dpconde.kaicare.AppDependencies
import com.dpconde.kaicare.core.localpersistence.di.LocalPersistenceModule
import com.dpconde.kaicare.core.remotepersistence.di.RemotePersistenceModule
import com.dpconde.kaicare.core.session.di.SessionModule
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [AppDependencies::class],
    modules = [
        ChatDirectoryModule::class,
        ChatDirectoryUIModule::class,
        SessionModule::class,
        LocalPersistenceModule::class,
        RemotePersistenceModule::class
    ]
)
interface ChatDirectoryUIComponent {
    fun inject (fragment: ChatDirectoryFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(daggerDependencies: AppDependencies): Builder
        fun build(): ChatDirectoryUIComponent
    }
}