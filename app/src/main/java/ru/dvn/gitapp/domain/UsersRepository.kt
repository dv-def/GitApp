package ru.dvn.gitapp.domain

import io.reactivex.rxjava3.core.Single

interface UsersRepository {
    fun getUsers(): Single<List<User>>

    fun getUserDetails(nickName: String): Single<UserDetails>
}