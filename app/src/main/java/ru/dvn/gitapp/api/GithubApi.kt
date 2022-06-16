package ru.dvn.gitapp.api

import retrofit2.Call
import retrofit2.http.GET
import ru.dvn.gitapp.data.remote.user.UserDTO

interface GithubApi {
    @GET("users")
    fun getUsers(): Call<List<UserDTO>>
}