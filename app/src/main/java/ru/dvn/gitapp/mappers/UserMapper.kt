package ru.dvn.gitapp.mappers

import ru.dvn.gitapp.data.remote.dto.UserDTO
import ru.dvn.gitapp.domain.models.User

fun UserDTO.toUser() = User(
    id = this.id,
    login = this.login,
    avatarUrl = this.avatarUrl
)