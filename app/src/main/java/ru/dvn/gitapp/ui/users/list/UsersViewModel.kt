package ru.dvn.gitapp.ui.users.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.kotlin.subscribeBy
import ru.dvn.gitapp.domain.GithubRepository
import ru.dvn.gitapp.domain.User
import ru.dvn.gitapp.ui.users.SingleEventLiveData

class UsersViewModel(private val repository: GithubRepository) : UsersContract.ViewModel {

    override val usersLiveData: LiveData<List<User>> = MutableLiveData()
    override val errorLiveData: LiveData<Throwable> = SingleEventLiveData()
    override val inProgressLiveData: LiveData<Boolean> = MutableLiveData()

    override fun onLoad() {
        inProgressLiveData.asMutable().postValue(true)
//        repository.getUsers(
//            onSuccess = { userList ->
//                usersLiveData.asMutable().postValue(userList)
//                inProgressLiveData.asMutable().postValue(false)
//            },
//            onError = { throwable ->
//                errorLiveData.asMutable().postValue(throwable)
//                inProgressLiveData.asMutable().postValue(false)
//            }
//        )
        repository.getUsers().subscribeBy(
            onSuccess = { usersList ->
                usersLiveData.asMutable().postValue(usersList)
                inProgressLiveData.asMutable().postValue(false)
            },
            onError = { t ->
                errorLiveData.asMutable().postValue(t)
                inProgressLiveData.asMutable().postValue(false)
            }
        )
    }

    private fun <T> LiveData<T>.asMutable() =
        this as? MutableLiveData<T> ?: throw IllegalStateException("Can't cast a LiveData")
}