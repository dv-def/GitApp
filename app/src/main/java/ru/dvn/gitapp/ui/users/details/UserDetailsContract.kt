package ru.dvn.gitapp.ui.users.details

import ru.dvn.gitapp.domain.UserDetails

interface UserDetailsContract {
    interface View {
        fun showUserDetails(userDetails: UserDetails)
        fun showError(t: Throwable)
        fun showProgress(inProgress: Boolean)
    }

    interface Presenter {
        fun attach(view: View)
        fun detach()
    }
}