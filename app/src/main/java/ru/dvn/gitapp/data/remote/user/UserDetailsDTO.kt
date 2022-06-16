package ru.dvn.gitapp.data.remote.user

import com.google.gson.annotations.SerializedName
import ru.dvn.gitapp.domain.UserDetails

data class UserDetailsDTO (
    @SerializedName("login")
    val login: String?,

    @SerializedName("avatar_url")
    val avatarUrl: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("public_repos")
    val publicRepos: Int?,

    @SerializedName("public_gists")
    val publicGists: Int?,

    @SerializedName("followers")
    val followers: Int?,

    @SerializedName("following")
    val following: Int?
)

fun UserDetailsDTO.toUserDetails():UserDetails = UserDetails(
    login = this.login,
    avatarUrl = this.avatarUrl,
    name = this.name,
    publicRepos = this.publicRepos,
    publicGists = this.publicGists,
    followers = this.followers,
    following = this.following,
)