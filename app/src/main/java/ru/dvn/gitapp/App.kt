package ru.dvn.gitapp

import android.app.Activity
import android.app.Application
import ru.dvn.dilib.DiStorage
import ru.dvn.gitapp.di.Module

class App : Application() {
    val diStore: DiStorage by lazy {
        DiStorage().apply {
            Module(applicationContext, this)
        }
    }
}

fun Activity.app() = applicationContext as App