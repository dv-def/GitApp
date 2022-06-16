package ru.dvn.gitapp.ui.users

import ru.dvn.gitapp.domain.GithubRepository
import ru.dvn.gitapp.domain.User

class UsersPresenter(
    private val repository: GithubRepository
) : UsersContract.Presenter {
    private var view: UsersContract.View? = null

    private var users: List<User>? = null
    private var loadingError: Throwable? = null
    private var inProgress: Boolean = false

    override fun attach(view: UsersContract.View) {
        this.view = view
        view.showProgress(inProgress)
        users?.let { view.showUsers(it) }
        loadingError?.let { view.showError(it) }
    }

    override fun detach() {
        this.view = null
    }

    override fun onLoad() {
       loadUsers()
    }

    private fun loadUsers() {
        view?.showProgress(inProgress = true)
        inProgress = true
        repository.getUsers(
            onSuccess = { userList ->
                view?.showUsers(userList)
                users = userList
                loadingError = null
                inProgress = false
            },
            onError = { throwable ->
                view?.showError(throwable)
                loadingError = throwable
                inProgress = false
            }
        )
    }
}