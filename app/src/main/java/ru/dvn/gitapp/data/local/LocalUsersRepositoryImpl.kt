package ru.dvn.gitapp.data.local

import android.util.Log
import io.reactivex.rxjava3.core.Single
import ru.dvn.gitapp.data.local.user.UserDao
import ru.dvn.gitapp.data.local.user.toEntity
import ru.dvn.gitapp.data.local.user.toUser
import ru.dvn.gitapp.data.local.user.toUserDetails
import ru.dvn.gitapp.domain.User
import ru.dvn.gitapp.domain.UserDetails
import ru.dvn.gitapp.domain.UsersRepository

class LocalUsersRepositoryImpl(private val userDao: UserDao) : UsersRepository {
    override fun getUsers(): Single<List<User>> {
        return userDao.getUsersList().map { usersList ->
            usersList.map { entity -> entity.toUser() }
        }
    }

    override fun getUserDetails(nickName: String): Single<UserDetails> {
        Log.e("RX", nickName)
        return userDao.getDetails(nickName).map { entity ->
            entity.toUserDetails()
        }
    }

    fun saveUsersList(users: List<User>) {
        userDao.insertList(users.map { it.toEntity() })
    }

    fun saveDetails(details: UserDetails) {
        userDao.insertDetails(details.toEntity())
    }
}