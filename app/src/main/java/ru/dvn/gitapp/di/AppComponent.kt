package ru.dvn.gitapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.dvn.gitapp.ui.users.details.UserDetailsActivity
import ru.dvn.gitapp.ui.users.list.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    RemoteModule::class,
    LocalModule::class
])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(userDetailsActivity: UserDetailsActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}