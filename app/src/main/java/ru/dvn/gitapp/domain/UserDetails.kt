package ru.dvn.gitapp.domain

data class UserDetails (
    val login: String?,
    val avatarUrl: String?,
    val name: String?,
    val publicRepos: Int?,
    val publicGists: Int?,
    val followers: Int?,
    val following: Int?
)