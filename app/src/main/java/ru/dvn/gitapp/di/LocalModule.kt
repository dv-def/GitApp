package ru.dvn.gitapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.dvn.gitapp.data.local.AppDatabase
import ru.dvn.gitapp.data.local.LocalUsersRepositoryImpl
import ru.dvn.gitapp.data.local.user.UserDao
import javax.inject.Singleton

@Module
class LocalModule {
    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return synchronized(AppDatabase::class.java) {
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                AppDatabase.DB_NAME
            ).build()
        }
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideLocalRepository(usersDao: UserDao): LocalUsersRepositoryImpl {
        return LocalUsersRepositoryImpl(usersDao)
    }

}