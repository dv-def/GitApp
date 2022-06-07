package ru.dvn.gitapp.data.remote

import ru.dvn.gitapp.api.GithubApi
import ru.dvn.gitapp.domain.GithubRepository
import ru.dvn.gitapp.domain.models.User
import ru.dvn.gitapp.mappers.toUser

class GithubRepositoryImpl(private val githubApi: GithubApi) : GithubRepository {
    override suspend fun getUsers(
        onSuccess: (List<User>) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        try {
            onSuccess.invoke(githubApi.getUsers().map { dto -> dto.toUser() } )
        } catch (t: Throwable) {
            onError.invoke(t)
        }
    }
}