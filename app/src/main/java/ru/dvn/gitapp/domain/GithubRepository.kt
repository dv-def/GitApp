package ru.dvn.gitapp.domain

interface GithubRepository {
    fun getUsers(
        onSuccess: (List<User>) -> Unit,
        onError: (t: Throwable) -> Unit,
    )
}