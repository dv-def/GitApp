package ru.dvn.gitapp.data.remote

import io.reactivex.rxjava3.core.Single
import ru.dvn.gitapp.data.remote.user.*
import ru.dvn.gitapp.domain.GithubRepository
import ru.dvn.gitapp.domain.User
import ru.dvn.gitapp.domain.UserDetails

class GithubRepositoryImpl(private val githubApi: GithubApi) : GithubRepository {
    override fun getUsers(): Single<List<User>> {
        return githubApi.getUsers().map { usersList ->
            usersList.map { dto -> dto.toUser() }
        }
    }

    override fun getUserDetails(nickName: String): Single<UserDetails> {
        return githubApi.getUserDetails(nickName).map { it.toUserDetails() }
    }
}