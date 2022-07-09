package ru.dvn.gitapp.di

import androidx.room.Room
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
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

val appModule = module {
    single<UsersRepository> { CachedUsersRepository(get(), get()) }
}

val localModule = module {
    single<AppDatabase> {
        synchronized(AppDatabase::class.java) {
            Room.databaseBuilder(
                androidContext(),
                AppDatabase::class.java,
                AppDatabase.DB_NAME
            ).build()
        }
    }

    single<UserDao> {
        get<AppDatabase>().userDao()
    }

    single<LocalUsersRepositoryImpl> { LocalUsersRepositoryImpl(get()) }
}

val remoteModule = module {
    val baseUrl = "https://api.github.com/"

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(buildHttpClient())
            .build()
    }

    single<GithubApi> {
        get<Retrofit>().create(GithubApi::class.java)
    }

    single<RemoteUsersRepositoryImpl>() { RemoteUsersRepositoryImpl(get()) }
}

private fun buildHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()
}

