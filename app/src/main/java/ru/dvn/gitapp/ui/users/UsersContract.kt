package ru.dvn.gitapp.ui.users

import ru.dvn.gitapp.domain.User

interface UsersContract {
    interface View {
        fun showUsers(users: List<User>)
        fun showError(throwable: Throwable)
        fun showProgress(inProgress: Boolean)
    }

    interface Presenter {
        fun attach(view: View)
        fun detach()

        fun onLoad()
    }
}