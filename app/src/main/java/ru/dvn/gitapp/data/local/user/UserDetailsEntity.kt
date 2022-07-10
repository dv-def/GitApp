package ru.dvn.gitapp.data.local.user

import androidx.room.*
import ru.dvn.gitapp.domain.UserDetails

@Entity(
    tableName = UserDetailsEntity.TABLE_NAME,
)
data class UserDetailsEntity (
    @PrimaryKey
    val id: Long,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "login")
    val login: String?,

    @ColumnInfo(name = "public_repos")
    val publicRepos: Int?,

    @ColumnInfo(name = "public_gists")
    val publicGists: Int?,

    @ColumnInfo(name = "followers")
    val followers: Int?,

    @ColumnInfo(name = "following")
    val following: Int?,
) {
    companion object {
        const val TABLE_NAME = "user_details"
    }
}

fun UserDetailsEntity.toUserDetails() = UserDetails(
    id = this.id,
    avatarUrl = null,
    name = this.name,
    login = this.login,
    publicRepos = this.publicRepos,
    publicGists = this.publicGists,
    followers = this.followers,
    following = this.following,
)

fun UserDetails.toEntity() = UserDetailsEntity(
    id = this.id,
    name = this.name,
    login = this.login,
    publicRepos = this.publicRepos,
    publicGists = this.publicGists,
    followers = this.followers,
    following = this.following,
)
