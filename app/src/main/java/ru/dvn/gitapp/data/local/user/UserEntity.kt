package ru.dvn.gitapp.data.local.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.dvn.gitapp.domain.User

@Entity(tableName = UserEntity.TABLE_NAME)
data class UserEntity (
    @PrimaryKey
    val id: Long,

    @ColumnInfo(name = "login")
    val login: String,
) {
    companion object {
        const val TABLE_NAME = "users"
    }
}

fun UserEntity.toUser() = User(id, login, null)

fun User.toEntity() = UserEntity(id, login)