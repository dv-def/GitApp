package ru.dvn.gitapp.ui.users.details

import io.reactivex.rxjava3.core.Observable
import ru.dvn.gitapp.domain.UserDetails

interface UserDetailsContract {

    interface ViewModel {
        val userDetails: Observable<UserDetails>
        val errors: Observable<Throwable>
        val inProgress: Observable<Boolean>

        fun loadDetails()
    }
}