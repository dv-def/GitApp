package ru.dvn.gitapp.domain.models

data class User(
    val id: Long,
    val login: String,
    val avatarUrl: String
)