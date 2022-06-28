package ru.dvn.gitapp.ui.users.list

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject
import ru.dvn.gitapp.domain.GithubRepository
import ru.dvn.gitapp.domain.User

class UsersViewModel(private val repository: GithubRepository) : UsersContract.ViewModel {

    override val users: Observable<List<User>> = BehaviorSubject.create()
    override val errors: Observable<Throwable> = BehaviorSubject.create()
    override val inProgress: Observable<Boolean> = BehaviorSubject.create()

    override fun onLoad() {
        inProgress.asSubject().onNext(true)
        repository.getUsers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
            onSuccess = { usersList ->
                users.asSubject().onNext(usersList)
                inProgress.asSubject().onNext(false)
            },
            onError = { t ->
                errors.asSubject().onNext(t)
                inProgress.asSubject().onNext(false)
            }
        )
    }

    private fun <T : Any> Observable<T>.asSubject(): Subject<T> {
        return this as? Subject<T> ?: throw IllegalStateException("It's not Observable")
    }
}