package ru.dvn.gitapp.ui.users.list

import io.reactivex.rxjava3.core.Observable
import ru.dvn.gitapp.domain.User

interface UsersContract {

    interface ViewModel {
        val users: Observable<List<User>>
        val errors: Observable<Throwable>
        val inProgress: Observable<Boolean>

        fun onLoad()
    }
}