package ru.dvn.gitapp

import android.app.Activity
import android.app.Application
import ru.dvn.gitapp.di.AppComponent
import ru.dvn.gitapp.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent by lazy { DaggerAppComponent.factory().create(this) }
}

fun Activity.app() = applicationContext as? App
    ?: throw IllegalStateException("NOT APPLICATION_CONTEXT")