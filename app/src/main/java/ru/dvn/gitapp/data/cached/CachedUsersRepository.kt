package ru.dvn.gitapp.data.cached

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import ru.dvn.gitapp.data.local.LocalUsersRepositoryImpl
import ru.dvn.gitapp.data.local.user.toEntity
import ru.dvn.gitapp.data.remote.RemoteUsersRepositoryImpl
import ru.dvn.gitapp.domain.User
import ru.dvn.gitapp.domain.UserDetails
import ru.dvn.gitapp.domain.UsersRepository

class CachedUsersRepository(
    private val remoteRepository: RemoteUsersRepositoryImpl,
    private val localRepository: LocalUsersRepositoryImpl
) : UsersRepository {

    override fun getUsers(): Single<List<User>> {
        return Single.create { emitter ->
            remoteRepository.getUsers().subscribeBy(
                onSuccess = {
                    emitter.onSuccess(it)
                    localRepository.saveUsersList(it)
                },
                onError = {
                    localRepository.getUsers().subscribeBy(
                        onSuccess = {
                            emitter.onSuccess(it)
                        },
                        onError = {
                            emitter.onError(it)
                        }
                    )
                }
            )
        }
    }

    override fun getUserDetails(nickName: String): Single<UserDetails> {
        return Single.create { emitter ->
            remoteRepository.getUserDetails(nickName).subscribeBy(
                onSuccess = {
                    emitter.onSuccess(it)
                    localRepository.saveDetails(it)
                },
                onError = {
                    localRepository.getUserDetails(nickName).subscribeBy(
                        onSuccess = {
                            emitter.onSuccess(it)
                        },
                        onError = {
                            emitter.onError(it)
                        }
                    )
                }
            )
        }
    }
}