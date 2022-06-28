package ru.dvn.gitapp.domain

import io.reactivex.rxjava3.core.Single

interface GithubRepository {
    fun getUsers(): Single<List<User>>

    fun getUserDetails(
        nickName: String,
        onSuccess: (UserDetails) -> Unit,
        onError: (t: Throwable) -> Unit
    )
}