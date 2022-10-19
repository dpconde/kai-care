package com.dpconde.kaicare.feature.login.di

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.dpconde.kaicare.AppDependencies
import com.dpconde.kaicare.feature.login.presentation.LoginFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [AppDependencies::class],
    modules = [
        LoginModule::class,
        LoginUIModule::class,
    ]
)
interface LoginUIComponent {
    fun inject (fragment: LoginFragment)

    @Component.Builder
    interface Builder {
        fun activity(@BindsInstance activity: FragmentActivity): Builder
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(daggerDependencies: AppDependencies): Builder
        fun build(): LoginUIComponent
    }
}