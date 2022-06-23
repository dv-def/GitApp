package ru.dvn.gitapp

import android.app.Activity
import android.app.Application
import ru.dvn.gitapp.data.fake.FakeGitHubRepositoryImpl
import ru.dvn.gitapp.data.remote.GitRetrofit
import ru.dvn.gitapp.data.remote.GithubRepositoryImpl
import ru.dvn.gitapp.domain.GithubRepository

class App : Application() {
//    val repository: GithubRepository by lazy { GithubRepositoryImpl(GitRetrofit.getGithubApi()) }
    val repository: GithubRepository by lazy { FakeGitHubRepositoryImpl() }
}

fun Activity.app() = applicationContext as App