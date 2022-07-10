package ru.dvn.gitapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.dvn.gitapp.domain.UsersRepository
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class
])
interface AppComponent {
    fun getUsersRepository(): UsersRepository

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}