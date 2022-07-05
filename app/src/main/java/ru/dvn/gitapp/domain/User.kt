package ru.dvn.gitapp.domain

data class User(
    val id: Long,
    val login: String,
    val avatarUrl: String?
)