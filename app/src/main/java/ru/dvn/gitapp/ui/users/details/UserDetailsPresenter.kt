package ru.dvn.gitapp.ui.users.details

import ru.dvn.gitapp.domain.GithubRepository
import ru.dvn.gitapp.domain.UserDetails

class UserDetailsPresenter(
    private val repository: GithubRepository,
    private val nickName: String
) : UserDetailsContract.Presenter {

    private var view: UserDetailsContract.View? = null

    private var details: UserDetails? = null
    private var inProgress: Boolean = false

    override fun attach(view: UserDetailsContract.View) {
        this.view = view
        view.showProgress(inProgress)
        details?.let { view.showUserDetails(it) } ?: run { loadDetails() }
    }

    override fun detach() {
        this.view = null
    }

    private fun loadDetails() {
        view?.showProgress(inProgress = true)
        inProgress = true
        repository.getUserDetails(
            this.nickName,
            onSuccess = { userDetails ->
                view?.showUserDetails(userDetails)
                details = userDetails
                inProgress = false
            },
            onError = { t ->
                view?.showError(t)
                inProgress = false
            }
        )
    }
}