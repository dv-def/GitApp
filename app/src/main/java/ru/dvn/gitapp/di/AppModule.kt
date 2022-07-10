package ru.dvn.gitapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
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
import javax.inject.Singleton

@Module
class AppModule() {
    // Remote block
    private val baseUrl = "https://api.github.com/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(buildHttpClient())
            .build()
    }

    @Provides
    @Singleton
    fun provideGithubApi(retrofit: Retrofit): GithubApi {
        return retrofit.create(GithubApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteRepository(api: GithubApi): RemoteUsersRepositoryImpl {
        return RemoteUsersRepositoryImpl(api)
    }

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

    // Local block
    @Provides
    @Singleton
    fun provideLocalRepository(usersDao: UserDao): LocalUsersRepositoryImpl {
        return LocalUsersRepositoryImpl(usersDao)
    }

    @Provides
    @Singleton
    fun provideUsersRepository(
        remoteRepository: RemoteUsersRepositoryImpl,
        localRepository: LocalUsersRepositoryImpl
    ): UsersRepository {
        return CachedUsersRepository(remoteRepository, localRepository)
    }

    private fun buildHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
        }.build()
    }
}
