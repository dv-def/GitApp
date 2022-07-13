package ru.dvn.gitapp

import android.app.Application
import ru.dvn.gitapp.di.Module

class App : Application() {
    init {
        Module(this)
    }
}
