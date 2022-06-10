package ru.dvn.gitapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserDTO (
    @SerializedName("id")
    val id: Long,

    @SerializedName("login")
    val login: String,

    @SerializedName("avatar_url")
    val avatarUrl: String
)