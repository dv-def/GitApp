package ru.dvn.gitapp

import android.app.Activity
import android.app.Application
import ru.dvn.gitapp.di.Dependencies

class App : Application() {
    val dependencies: Dependencies by lazy {
        Dependencies(applicationContext)
    }
}

fun Activity.app() = applicationContext as App