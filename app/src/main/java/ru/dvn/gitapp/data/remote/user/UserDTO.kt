package ru.dvn.gitapp.data.remote.user

import com.google.gson.annotations.SerializedName
import ru.dvn.gitapp.domain.User

data class UserDTO (
    @SerializedName("id")
    val id: Long,

    @SerializedName("login")
    val login: String,

    @SerializedName("avatar_url")
    val avatarUrl: String
)

fun UserDTO.toUser() = User(
    id = this.id,
    login = this.login,
    avatarUrl = this.avatarUrl
)