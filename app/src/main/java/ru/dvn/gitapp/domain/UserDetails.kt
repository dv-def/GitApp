package ru.dvn.gitapp.domain

data class UserDetails (
    val id: Long,
    val avatarUrl: String?,
    val name: String?,
    val login: String?,
    val publicRepos: Int?,
    val publicGists: Int?,
    val followers: Int?,
    val following: Int?
)