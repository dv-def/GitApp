package ru.dvn.gitapp.di

import dagger.Module
import dagger.Provides
import ru.dvn.gitapp.data.cached.CachedUsersRepository
import ru.dvn.gitapp.data.local.LocalUsersRepositoryImpl
import ru.dvn.gitapp.data.remote.RemoteUsersRepositoryImpl
import ru.dvn.gitapp.domain.UsersRepository
import ru.dvn.gitapp.ui.users.details.UserDetailsViewModel
import ru.dvn.gitapp.ui.users.list.UsersViewModel
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideUsersRepository(
        remoteRepository: RemoteUsersRepositoryImpl,
        localRepository: LocalUsersRepositoryImpl
    ): UsersRepository {
        return CachedUsersRepository(remoteRepository, localRepository)
    }

    @Provides
    @Singleton
    fun provideUsersViewModel(usersRepository: UsersRepository): UsersViewModel {
        return UsersViewModel(usersRepository)
    }

    @Provides
    @Singleton
    fun provideUserDetailsViewModel(usersRepository: UsersRepository): UserDetailsViewModel {
        return UserDetailsViewModel(usersRepository)
    }
}
