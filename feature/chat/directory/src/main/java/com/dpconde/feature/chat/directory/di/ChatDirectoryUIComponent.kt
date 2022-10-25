package com.dpconde.feature.chat.directory.di

import android.content.Context
import com.dpconde.feature.chat.directory.presentation.ChatDirectoryFragment
import com.dpconde.kaicare.AppDependencies
import com.dpconde.kaicare.core.persistence.di.PersistenceModule
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [AppDependencies::class],
    modules = [
        ChatDirectoryModule::class,
        ChatDirectoryUIModule::class,
        PersistenceModule::class
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