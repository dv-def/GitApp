package ru.dvn.gitapp.ui.users.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.dvn.gitapp.domain.GithubRepository
import ru.dvn.gitapp.domain.UserDetails
import ru.dvn.gitapp.ui.users.SingleEventLiveData

class UserDetailsViewModel(
    private val repository: GithubRepository,
    private val nickName: String
) : UserDetailsContract.ViewModel {

    override val userDetailsLiveData: LiveData<UserDetails> = MutableLiveData()
    override val errorLiveData: LiveData<Throwable> = SingleEventLiveData()
    override val inProgressLiveData: LiveData<Boolean> = MutableLiveData()

    override fun loadDetails() {
        inProgressLiveData.asMutable().postValue(true)
        repository.getUserDetails(
            this.nickName,
            onSuccess = { userDetails ->
                userDetailsLiveData.asMutable().postValue(userDetails)
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