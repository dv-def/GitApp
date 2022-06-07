package ru.dvn.gitapp.api

import retrofit2.http.GET
import ru.dvn.gitapp.data.remote.dto.UserDTO
import ru.dvn.gitapp.domain.models.User

interface GithubApi {
    @GET("users")
    suspend fun getUsers(): List<UserDTO>
}