package ru.dvn.gitapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.dvn.gitapp.di.appModule
import ru.dvn.gitapp.di.localModule
import ru.dvn.gitapp.di.remoteModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                localModule,
                remoteModule,
                appModule
            )
        }
    }
}