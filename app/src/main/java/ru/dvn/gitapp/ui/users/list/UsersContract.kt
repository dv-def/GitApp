package ru.dvn.gitapp.ui.users.list

import ru.dvn.gitapp.domain.User

interface UsersContract {
    interface View {
        fun showUsers(users: List<User>)
        fun showError(throwable: Throwable)
        fun showProgress(inProgress: Boolean)
        fun goToDetails(nickName: String)
    }

    interface Presenter {
        fun attach(view: View)
        fun detach()

        fun onLoad()
        fun onGoToDetails(nickName: String)
    }
}