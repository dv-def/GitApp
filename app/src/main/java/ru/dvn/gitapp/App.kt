package ru.dvn.gitapp

import android.app.Activity
import android.app.Application
import ru.dvn.gitapp.di.DiStore
import ru.dvn.gitapp.di.Module

class App : Application() {
    val diStore: DiStore by lazy {
        DiStore().apply {
            Module(applicationContext, this)
        }
    }
}

fun Activity.app() = applicationContext as App