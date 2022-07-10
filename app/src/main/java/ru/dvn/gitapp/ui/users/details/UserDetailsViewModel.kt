package ru.dvn.gitapp.ui.users.details

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject
import ru.dvn.gitapp.domain.UsersRepository
import ru.dvn.gitapp.domain.UserDetails

class UserDetailsViewModel(
    private val repository: UsersRepository,
) : UserDetailsContract.ViewModel, ViewModel() {

    override val userDetails: Observable<UserDetails> = BehaviorSubject.create()
    override val errors: Observable<Throwable> = BehaviorSubject.create()
    override val inProgress: Observable<Boolean> = BehaviorSubject.create()

    override fun loadDetails(nickName: String) {
        inProgress.asSubject().onNext(true)
        repository.getUserDetails(nickName)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { profile ->
                    inProgress.asSubject().onNext(false)
                    userDetails.asSubject().onNext(profile)
                },
                onError = { t ->
                    inProgress.asSubject().onNext(false)
                    errors.asSubject().onNext(t)
                }
            )
    }

    private fun <T: Any> Observable<T>.asSubject(): Subject<T> {
        return this as? Subject<T> ?: throw IllegalAccessError("It's not observable")
    }
}