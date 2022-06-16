package ru.dvn.gitapp.data.remote.user

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ru.dvn.gitapp.data.remote.user.UserDTO

interface GithubApi {
    @GET("users")
    fun getUsers(): Call<List<UserDTO>>

    @GET("users/{nick_name}")
    fun getUserDetails(@Path("nick_name") nickName: String): Call<UserDetailsDTO>
}