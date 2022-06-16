package ru.dvn.gitapp.ui.users

import ru.dvn.gitapp.domain.GithubRepository
import ru.dvn.gitapp.domain.User

class UsersPresenter(
    private val repository: GithubRepository
) : UsersContract.Presenter {
    private var view: UsersContract.View? = null

    private var users: List<User>? = null
    private var inProgress: Boolean = false

    override fun attach(view: UsersContract.View) {
        this.view = view
        view.showProgress(inProgress)
        users?.let { view.showUsers(it) }
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
                inProgress = false
            },
            onError = { throwable ->
                view?.showError(throwable)
                inProgress = false
            }
        )
    }
}