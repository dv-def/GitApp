package ru.dvn.gitapp.di

import dagger.Module
import dagger.Provides
import ru.dvn.gitapp.data.cached.CachedUsersRepository
import ru.dvn.gitapp.data.local.LocalUsersRepositoryImpl
import ru.dvn.gitapp.data.remote.RemoteUsersRepositoryImpl
import ru.dvn.gitapp.domain.UsersRepository
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
}
