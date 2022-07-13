package ru.dvn.gitapp.di

import android.content.Context
import androidx.room.Room
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.dvn.gitapp.data.cached.CachedUsersRepository
import ru.dvn.gitapp.data.local.AppDatabase
import ru.dvn.gitapp.data.local.LocalUsersRepositoryImpl
import ru.dvn.gitapp.data.local.user.UserDao
import ru.dvn.gitapp.data.remote.RemoteUsersRepositoryImpl
import ru.dvn.gitapp.data.remote.user.GithubApi
import ru.dvn.gitapp.domain.UsersRepository

class Dependencies(context: Context) {
    private lateinit var applicationDatabase: AppDatabase
    private val userDao: UserDao by lazy {
        applicationDatabase.userDao()
    }

    private val baseUrl = "https://api.github.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(buildHttpClient())
        .build()

    private val gitHubApi = retrofit.create(GithubApi::class.java)

    val repository: UsersRepository by lazy {
        CachedUsersRepository(
            RemoteUsersRepositoryImpl(gitHubApi),
            LocalUsersRepositoryImpl(userDao)
        )
    }

    private fun buildHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
    }

    init {
        synchronized(AppDatabase::class.java) {
            applicationDatabase = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                AppDatabase.DB_NAME
            ).build()
        }
    }
}