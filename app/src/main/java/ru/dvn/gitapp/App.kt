package ru.dvn.gitapp

import android.app.Activity
import android.app.Application
import ru.dvn.gitapp.data.FakeGitHubRepositoryImpl
import ru.dvn.gitapp.domain.GithubRepository

class App : Application() {
    val repository: GithubRepository by lazy { FakeGitHubRepositoryImpl() }
}

fun Activity.app() = applicationContext as App