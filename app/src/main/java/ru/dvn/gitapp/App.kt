package ru.dvn.gitapp

import android.app.Activity
import android.app.Application
import androidx.room.Room
import ru.dvn.gitapp.data.cached.CachedUsersRepository
import ru.dvn.gitapp.data.local.AppDatabase
import ru.dvn.gitapp.data.local.LocalUsersRepositoryImpl
import ru.dvn.gitapp.data.remote.GitRetrofit
import ru.dvn.gitapp.data.remote.RemoteUsersRepositoryImpl
import ru.dvn.gitapp.domain.UsersRepository

class App : Application() {
    private lateinit var applicationDatabase: AppDatabase

    val remoteRepository: UsersRepository by lazy {
        RemoteUsersRepositoryImpl(GitRetrofit.getGithubApi())
    }

    val cachedRepository: UsersRepository by lazy {
        CachedUsersRepository(
            RemoteUsersRepositoryImpl(GitRetrofit.getGithubApi()),
            LocalUsersRepositoryImpl(applicationDatabase.userDao())
        )
    }

    override fun onCreate() {
        super.onCreate()
        synchronized(AppDatabase::class.java) {
            applicationDatabase = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                AppDatabase.DB_NAME
            ).build()
        }
    }
}

fun Activity.app() = applicationContext as App