package ru.dvn.gitapp.domain

import ru.dvn.gitapp.domain.models.User

interface GithubRepository {
    suspend fun getUsers(
        onSuccess: (List<User>) -> Unit,
        onError: (t: Throwable) -> Unit,
    )
}