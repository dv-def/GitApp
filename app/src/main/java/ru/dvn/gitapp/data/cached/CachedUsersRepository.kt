package ru.dvn.gitapp.data.cached

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleEmitter
import io.reactivex.rxjava3.core.SingleOnSubscribe
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.Subject
import ru.dvn.gitapp.data.local.LocalUsersRepositoryImpl
import ru.dvn.gitapp.data.remote.RemoteUsersRepositoryImpl
import ru.dvn.gitapp.domain.User
import ru.dvn.gitapp.domain.UserDetails
import ru.dvn.gitapp.domain.UsersRepository

class CachedUsersRepository(
    private val remoteRepository: RemoteUsersRepositoryImpl,
    private val localRepository: LocalUsersRepositoryImpl
) : UsersRepository {

    override fun getUsers(): Single<List<User>> {
        return Single.create(object: SingleOnSubscribe<List<User>> {
            override fun subscribe(emitter: SingleEmitter<List<User>>) {
                remoteRepository.getUsers().subscribeBy(
                    onSuccess = {
                        emitter.onSuccess(it)
                        localRepository.saveUsers(it)
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
        })
    }

    override fun getUserDetails(nickName: String): Single<UserDetails> {
        TODO("Not yet implemented")
    }

    private fun <T: Any> Observable<T>.asSubject(): Subject<T> {
        return this as? Subject<T> ?: throw IllegalAccessError("Can't cast to Subject")
    }
}